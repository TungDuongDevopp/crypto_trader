package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Holding.AddHoldingRequest;
import com.coin.cryptocurrency_trading_management.DTO.Holding.DeductHoldingRequest;
import com.coin.cryptocurrency_trading_management.DTO.Order.OrderRequestCreate;
import com.coin.cryptocurrency_trading_management.DTO.Transaction.TransactionRequestCreate;
import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderStatus;
import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderType;
import com.coin.cryptocurrency_trading_management.Entity.Enum.Side;
import com.coin.cryptocurrency_trading_management.Entity.Order;
import com.coin.cryptocurrency_trading_management.Repository.Interface.CoinRepository;
import com.coin.cryptocurrency_trading_management.Repository.Interface.OrderRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.HoldingService;
import com.coin.cryptocurrency_trading_management.Service.Interface.OrderService;
import com.coin.cryptocurrency_trading_management.Service.Interface.TransactionService;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CoinRepository coinRepository;
    private final WalletService walletService;
    private final HoldingService holdingService;
    private final TransactionService transactionService;

    // ═══════════════════════════════════════════════════════
    //  BUY COIN
    // ═══════════════════════════════════════════════════════
    @Override
    public void buyCoin(long userId, OrderRequestCreate request) throws IOException {
        // 1. Kiểm tra coin tồn tại
        var coin = coinRepository.getById(request.getCoinId());
        if (coin == null) {
            throw new ResourceNotFoundException("Coin not found: " + request.getCoinId());
        }

        BigDecimal executionPrice = resolveExecutionPrice(request, coin.getPrice());

        BigDecimal totalCost = executionPrice.multiply(request.getQuantity());

        walletService.deductBalance(userId, totalCost);

        Order order = buildOrder(userId, request, Side.LONG);

        if (request.getOrderType() == OrderType.Market) {
            order.setFilledPrice(executionPrice);
            order.setOrderStatus(OrderStatus.FILLED);
            orderRepository.save(order);

            AddHoldingRequest addReq = new AddHoldingRequest();
            addReq.setUserId(userId);
            addReq.setCoinId(request.getCoinId());
            addReq.setQuantity(request.getQuantity());
            addReq.setPrice(executionPrice);
            holdingService.addHolding(addReq);

            createTransaction(order.getId(), request.getQuantity(), executionPrice);

        } else {
            // LIMIT → lưu lệnh chờ, chưa fill
            order.setOrderStatus(OrderStatus.NEW);
            orderRepository.save(order);
            // Việc fill sẽ do orderMatch() xử lý khi giá chạm
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SELL COIN
    // ═══════════════════════════════════════════════════════
    @Override
    public void sellCoin(long userId, OrderRequestCreate request) throws IOException {
        // 1. Kiểm tra coin tồn tại
        var coin = coinRepository.getById(request.getCoinId());
        if (coin == null) {
            throw new ResourceNotFoundException("Coin not found: " + request.getCoinId());
        }

        // 2. Xác định executionPrice
        BigDecimal executionPrice = resolveExecutionPrice(request, coin.getPrice());

        // 3. Tạo Order và lưu
        Order order = buildOrder(userId, request, Side.SHORT);

        if (request.getOrderType() == OrderType.Market) {
            // MARKET → fill ngay
            order.setFilledPrice(executionPrice);
            order.setOrderStatus(OrderStatus.FILLED);
            orderRepository.save(order);

            // 4. Trừ Holding (kiểm tra số lượng bên trong, throw nếu không đủ)
            DeductHoldingRequest deductReq = new DeductHoldingRequest();
            deductReq.setUserId(userId);
            deductReq.setCoinId(request.getCoinId());
            deductReq.setQuantity(request.getQuantity());
            deductReq.setPrice(executionPrice);
            holdingService.deductHolding(deductReq);

            // 5. Cộng tiền ví
            BigDecimal revenue = executionPrice.multiply(request.getQuantity());
            walletService.addBalance(userId, revenue);

            // 6. Ghi Transaction
            createTransaction(order.getId(), request.getQuantity(), executionPrice);

        } else {
            // LIMIT → lưu chờ
            order.setOrderStatus(OrderStatus.NEW);
            orderRepository.save(order);
        }
    }

    // ═══════════════════════════════════════════════════════
    //  ORDER MATCH (dùng cho Limit Order khi giá chạm)
    // ═══════════════════════════════════════════════════════
    @Override
    public void orderMatch(long orderId) throws IOException {
        Order order = orderRepository.getById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found: " + orderId);
        }
        if (order.getOrderStatus() != OrderStatus.NEW) {
            return; // Đã xử lý rồi, bỏ qua
        }

        BigDecimal executionPrice = order.getPrice(); // Limit price

        if (order.getSide() == Side.LONG) {
            // BUY limit matched
            AddHoldingRequest addReq = new AddHoldingRequest();
            addReq.setUserId(order.getUserId());
            addReq.setCoinId(order.getCoinId());
            addReq.setQuantity(order.getQuantity());
            addReq.setPrice(executionPrice);
            holdingService.addHolding(addReq);
        } else {
            // SELL limit matched
            DeductHoldingRequest deductReq = new DeductHoldingRequest();
            deductReq.setUserId(order.getUserId());
            deductReq.setCoinId(order.getCoinId());
            deductReq.setQuantity(order.getQuantity());
            deductReq.setPrice(executionPrice);
            holdingService.deductHolding(deductReq);

            BigDecimal revenue = executionPrice.multiply(order.getQuantity());
            walletService.addBalance(order.getUserId(), revenue);
        }

        // Ghi Transaction
        createTransaction(order.getId(), order.getQuantity(), executionPrice);

        // Cập nhật Order thành FILLED
        order.setFilledPrice(executionPrice);
        order.setOrderStatus(OrderStatus.FILLED);
        orderRepository.updateById(orderId, order);
    }

    // ═══════════════════════════════════════════════════════
    //  PRIVATE HELPERS
    // ═══════════════════════════════════════════════════════

    /**
     * Xác định giá thực thi:
     * - Market  → dùng giá thị trường hiện tại (marketPrice)
     * - Limit   → dùng giá do user đặt (request.getPrice())
     */
    private BigDecimal resolveExecutionPrice(OrderRequestCreate request, BigDecimal marketPrice) {
        if (request.getOrderType() == OrderType.Market) {
            return marketPrice;              // null price → lấy giá thị trường
        }
        return request.getPrice();           // Limit → dùng giá đặt
    }

    /** Khởi tạo Order từ request (chưa set status và filledPrice) */
    private Order buildOrder(long userId, OrderRequestCreate request, Side side) {
        Order order = new Order();
        order.setUserId(userId);
        order.setCoinId(request.getCoinId());
        order.setOrderType(request.getOrderType());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice()); // null nếu Market, OK
        order.setSide(side);
        return order;
    }

    /** Ghi lại transaction sau khi khớp lệnh */
    private void createTransaction(long orderId, BigDecimal quantity, BigDecimal executionPrice) throws IOException {
        TransactionRequestCreate txReq = new TransactionRequestCreate();
        txReq.setOrderId(orderId);
        txReq.setQuantity(quantity);
        txReq.setExecutionPrice(executionPrice);
        transactionService.createTransaction(txReq);
    }
}
