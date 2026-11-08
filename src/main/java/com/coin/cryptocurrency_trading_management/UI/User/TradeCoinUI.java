package com.coin.cryptocurrency_trading_management.UI.User;

import com.coin.cryptocurrency_trading_management.Controller.CoinController;
import com.coin.cryptocurrency_trading_management.Controller.HoldingController;
import com.coin.cryptocurrency_trading_management.Controller.OrderController;
import com.coin.cryptocurrency_trading_management.Controller.WalletController;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinResponse;
import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;
import com.coin.cryptocurrency_trading_management.DTO.Order.OrderRequestCreate;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.UserWalletResponse;
import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderType;
import com.coin.cryptocurrency_trading_management.Util.ConsoleColor;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class TradeCoinUI {

    private final Scanner scanner;
    private final WalletController walletController;
    private final HoldingController holdingController;
    private final CoinController coinController;
    private final OrderController orderController;

    public void buyCoinForm(long coinId) {
        CoinResponse coin;
        try {
            coin = coinController.getCoinById(coinId);
        } catch (Exception e) {
            System.out.println(ConsoleColor.RED + "  Không tìm thấy coin với ID: " + coinId + ConsoleColor.RESET);
            return;
        }

        UserWalletResponse myWallet = walletController.getWalletInfo();
        if (myWallet == null) {
            System.out.println(ConsoleColor.RED + "  Không thể lấy thông tin ví. Vui lòng đăng nhập lại!" + ConsoleColor.RESET);
            return;
        }

        BigDecimal walletBalance = myWallet.getBalance() != null ? myWallet.getBalance() : BigDecimal.ZERO;

        System.out.println();
        System.out.println(ConsoleColor.CYAN + "===================== BUY COIN =====================" + ConsoleColor.RESET);
        System.out.printf("  Coin            : %s (%s)%n", coin.getName(), coin.getSymbol());
        System.out.printf("  Giá thị trường  : $%.2f%n", coin.getPrice());
        System.out.printf("  Số dư ví        : $%.2f%n", walletBalance);
        System.out.println(ConsoleColor.CYAN + "----------------------------------------------------" + ConsoleColor.RESET);

        if (walletBalance.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(ConsoleColor.YELLOW + "  Số dư ví của bạn là 0$. Vui lòng nạp thêm tiền để giao dịch!" + ConsoleColor.RESET);
            return;
        }

        // 1. Nhập Giá (Enter = Market)
        System.out.print("  Nhập giá đặt (Enter để mua theo giá thị trường - Market): ");
        String priceInput = scanner.nextLine().trim();

        BigDecimal price = null;
        OrderType orderType = OrderType.Market;

        if (!priceInput.isEmpty()) {
            try {
                price = new BigDecimal(priceInput);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println(ConsoleColor.RED + "  Giá đặt phải lớn hơn 0!" + ConsoleColor.RESET);
                    return;
                }
                orderType = OrderType.Limit;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "  Giá nhập không hợp lệ!" + ConsoleColor.RESET);
                return;
            }
        }

        BigDecimal effectivePrice = (price != null) ? price : coin.getPrice();

        // 2. Nhập Số lượng (Enter = All-in)
        BigDecimal maxQty = walletBalance.divide(effectivePrice, 6, RoundingMode.DOWN);
        System.out.printf("  Nhập số lượng (Enter để mua tối đa: %.6f %s): ", maxQty, coin.getSymbol());
        String qtyInput = scanner.nextLine().trim();

        BigDecimal quantity;
        if (qtyInput.isEmpty()) {
            quantity = maxQty;
            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println(ConsoleColor.RED + "  Số dư không đủ để mua số lượng tối thiểu!" + ConsoleColor.RESET);
                return;
            }
        } else {
            try {
                quantity = new BigDecimal(qtyInput);
                if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println(ConsoleColor.RED + "  Số lượng mua phải lớn hơn 0!" + ConsoleColor.RESET);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "  Số lượng nhập không hợp lệ!" + ConsoleColor.RESET);
                return;
            }
        }

        BigDecimal totalEstimated = effectivePrice.multiply(quantity);

        // 3. Tóm tắt & xác nhận
        System.out.println(ConsoleColor.CYAN + "----------------------------------------------------" + ConsoleColor.RESET);
        System.out.printf("  Loại lệnh       : %s%n", orderType);
        System.out.printf("  Giá khớp dự kiến: $%.2f%n", effectivePrice);
        System.out.printf("  Số lượng        : %.6f %s%n", quantity, coin.getSymbol());
        System.out.printf("  Tổng thanh toán : $%.2f%n", totalEstimated);
        System.out.print("  Xác nhận đặt lệnh mua? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println(ConsoleColor.YELLOW + "  Đã hủy đặt lệnh mua." + ConsoleColor.RESET);
            return;
        }

        // 4. Thực thi Order qua OrderController
        try {
            OrderRequestCreate request = new OrderRequestCreate();
            request.setCoinId(coinId);
            request.setOrderType(orderType);
            request.setPrice(price);
            request.setQuantity(quantity);

            orderController.buyCoin(request);

            System.out.println(ConsoleColor.GREEN + ConsoleColor.BOLD
                    + "  Đặt lệnh mua thành công!" + ConsoleColor.RESET);
        } catch (BusinessException e) {
            System.out.println(ConsoleColor.RED + "  Giao dịch thất bại: " + e.getMessage() + ConsoleColor.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Lỗi hệ thống khi lưu đơn hàng. Vui lòng thử lại!" + ConsoleColor.RESET);
        }
    }

    public void sellCoinForm(long coinId) {
        CoinResponse coin;
        try {
            coin = coinController.getCoinById(coinId);
        } catch (Exception e) {
            System.out.println(ConsoleColor.RED + "  Không tìm thấy coin với ID: " + coinId + ConsoleColor.RESET);
            return;
        }

        BigDecimal availableCoin = BigDecimal.ZERO;
        try {
            HoldingResponse holding = holdingController.getHoldingByUserIdAndCoinId(coinId);
            if (holding != null && holding.getQuantity() != null) {
                availableCoin = holding.getQuantity();
            }
        } catch (ResourceNotFoundException e) {
            availableCoin = BigDecimal.ZERO;
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Không thể kiểm tra holding. Vui lòng thử lại!" + ConsoleColor.RESET);
            return;
        }

        System.out.println();
        System.out.println(ConsoleColor.CYAN + "===================== SELL COIN ====================" + ConsoleColor.RESET);
        System.out.printf("  Coin            : %s (%s)%n", coin.getName(), coin.getSymbol());
        System.out.printf("  Giá thị trường  : $%.2f%n", coin.getPrice());
        System.out.printf("  Số coin có thể bán: %.6f %s%n", availableCoin, coin.getSymbol());
        System.out.println(ConsoleColor.CYAN + "----------------------------------------------------" + ConsoleColor.RESET);

        if (availableCoin.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(ConsoleColor.YELLOW + "  Bạn không có coin này trong ví holding để bán!" + ConsoleColor.RESET);
            return;
        }

        // 1. Nhập Giá (Enter = Market)
        System.out.print("  Nhập giá đặt (Enter để bán theo giá thị trường - Market): ");
        String priceInput = scanner.nextLine().trim();

        BigDecimal price = null;
        OrderType orderType = OrderType.Market;

        if (!priceInput.isEmpty()) {
            try {
                price = new BigDecimal(priceInput);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println(ConsoleColor.RED + "  Giá bán phải lớn hơn 0!" + ConsoleColor.RESET);
                    return;
                }
                orderType = OrderType.Limit;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "  Giá nhập không hợp lệ!" + ConsoleColor.RESET);
                return;
            }
        }

        BigDecimal effectivePrice = (price != null) ? price : coin.getPrice();

        // 2. Nhập Số lượng (Enter = Bán hết)
        System.out.printf("  Nhập số lượng (Enter để bán hết: %.6f %s): ", availableCoin, coin.getSymbol());
        String qtyInput = scanner.nextLine().trim();

        BigDecimal quantity;
        if (qtyInput.isEmpty()) {
            quantity = availableCoin;
        } else {
            try {
                quantity = new BigDecimal(qtyInput);
                if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println(ConsoleColor.RED + "  Số lượng bán phải lớn hơn 0!" + ConsoleColor.RESET);
                    return;
                }
                if (quantity.compareTo(availableCoin) > 0) {
                    System.out.println(ConsoleColor.RED + "  Số lượng bán vượt quá số dư coin hiện có!" + ConsoleColor.RESET);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColor.RED + "  Số lượng nhập không hợp lệ!" + ConsoleColor.RESET);
                return;
            }
        }

        BigDecimal totalRevenue = effectivePrice.multiply(quantity);

        // 3. Tóm tắt & xác nhận
        System.out.println(ConsoleColor.CYAN + "----------------------------------------------------" + ConsoleColor.RESET);
        System.out.printf("  Loại lệnh       : %s%n", orderType);
        System.out.printf("  Giá khớp dự kiến: $%.2f%n", effectivePrice);
        System.out.printf("  Số lượng bán    : %.6f %s%n", quantity, coin.getSymbol());
        System.out.printf("  Ước tính thu về : $%.2f%n", totalRevenue);
        System.out.print("  Xác nhận đặt lệnh bán? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println(ConsoleColor.YELLOW + "  Đã hủy đặt lệnh bán." + ConsoleColor.RESET);
            return;
        }

        // 4. Thực thi Order qua OrderController
        try {
            OrderRequestCreate request = new OrderRequestCreate();
            request.setCoinId(coinId);
            request.setOrderType(orderType);
            request.setPrice(price);
            request.setQuantity(quantity);

            orderController.sellCoin(request);

            System.out.println(ConsoleColor.GREEN + ConsoleColor.BOLD
                    + "  Đặt lệnh bán thành công!" + ConsoleColor.RESET);
        } catch (BusinessException e) {
            System.out.println(ConsoleColor.RED + "  Giao dịch thất bại: " + e.getMessage() + ConsoleColor.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "  Lỗi hệ thống khi lưu đơn hàng. Vui lòng thử lại!" + ConsoleColor.RESET);
        }
    }
}
