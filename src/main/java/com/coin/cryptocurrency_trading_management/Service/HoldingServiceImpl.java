package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Holding.AddHoldingRequest;
import com.coin.cryptocurrency_trading_management.DTO.Holding.DeductHoldingRequest;
import com.coin.cryptocurrency_trading_management.DTO.Holding.DeductHoldingResponse;
import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;
import com.coin.cryptocurrency_trading_management.Entity.Holding;
import com.coin.cryptocurrency_trading_management.Repository.Interface.CoinRepository;
import com.coin.cryptocurrency_trading_management.Repository.Interface.HoldingRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.HoldingService;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import com.coin.cryptocurrency_trading_management.Util.Mapper.HoldingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HoldingServiceImpl implements HoldingService {

    private final HoldingRepository holdingRepository;
    private final CoinRepository coinRepository;
    private final HoldingMapper holdingMapper;

    @Override
    public List<HoldingResponse> getAll() throws IOException {
        var holdings = holdingRepository.getAll();
        var responses = holdingMapper.toResponseList(holdings);
        enrichHoldingResponses(responses);
        return responses;
    }

    @Override
    public List<HoldingResponse> getHoldingsByUserId(long userId) throws IOException {
        var holdings = holdingRepository.getHoldingsByUserId(userId);
        var responses = holdingMapper.toResponseList(holdings);
        enrichHoldingResponses(responses);
        return responses;
    }

    @Override
    public HoldingResponse getHoldingByUserIdAndCoinId(long userId, long coinId) throws IOException {
        var holding = holdingRepository.getHoldingByUserIdAndCoinId(userId, coinId);
        if (holding == null) {
            throw new ResourceNotFoundException("Holding not found for user: " + userId + " and coin: " + coinId);
        }
        var response = holdingMapper.toResponse(holding);
        enrichSingleResponse(response);
        return response;
    }

    @Override
    public void addHolding(AddHoldingRequest request) throws IOException {
        Holding holding = holdingRepository.getHoldingByUserIdAndCoinId(request.getUserId(), request.getCoinId());

        // 1. Mua lần đầu
        if (holding == null) {
            Holding newHolding = new Holding();
            newHolding.setUserId(request.getUserId());
            newHolding.setCoinId(request.getCoinId());
            newHolding.setQuantity(request.getQuantity());
            newHolding.setAverageBuyPrice(request.getPrice()); // Giá mua lần đầu chính là giá trung bình
            holdingRepository.save(newHolding);
            return;
        }

        // 2. Mua thêm (Tính lại giá mua trung bình - DCA)
        BigDecimal oldQty = holding.getQuantity();
        BigDecimal oldAvgPrice = holding.getAverageBuyPrice();

        BigDecimal totalOldCost = oldQty.multiply(oldAvgPrice);
        BigDecimal totalNewCost = request.getQuantity().multiply(request.getPrice());
        BigDecimal newTotalQty = oldQty.add(request.getQuantity());

        BigDecimal newAvgPrice = totalOldCost.add(totalNewCost)
                .divide(newTotalQty, 8, RoundingMode.HALF_UP);

        holding.setQuantity(newTotalQty);
        holding.setAverageBuyPrice(newAvgPrice);
        holdingRepository.updateById(holding.getId(), holding);
    }

    @Override
    public DeductHoldingResponse deductHolding(DeductHoldingRequest request) throws IOException {
        Holding holding = holdingRepository.getHoldingByUserIdAndCoinId(request.getUserId(), request.getCoinId());

        if (holding == null || holding.getQuantity().compareTo(request.getQuantity()) < 0) {
            throw new BusinessException("Insufficient coin balance in holding to deduct!");
        }

        BigDecimal remainingQty = holding.getQuantity().subtract(request.getQuantity());

        // Gọi hàm riêng tính toán Lãi/Lỗ chốt (Realized PnL) và doanh thu bán
        DeductHoldingResponse pnlResult = calculateRealizedPnL(
                request.getQuantity(),
                holding.getAverageBuyPrice(),
                request.getPrice(),
                remainingQty
        );

        // Cập nhật hoặc xóa bản ghi holding
        if (remainingQty.compareTo(BigDecimal.ZERO) == 0) {
            holdingRepository.deleteById(holding.getId());
        } else {
            holding.setQuantity(remainingQty);
            // Lưu ý: averageBuyPrice giữ nguyên khi bán
            holdingRepository.updateById(holding.getId(), holding);
        }

        return pnlResult;
    }

    /**
     * Hàm riêng tính toán Lãi / Lỗ đã chốt khi bán coin (Realized PnL)
     */
    private DeductHoldingResponse calculateRealizedPnL(BigDecimal sellQuantity,
                                                      BigDecimal averageBuyPrice,
                                                      BigDecimal sellPrice,
                                                      BigDecimal remainingQuantity) {
        BigDecimal revenue = sellQuantity.multiply(sellPrice);
        BigDecimal costBasis = sellQuantity.multiply(averageBuyPrice);
        BigDecimal profitOrLoss = revenue.subtract(costBasis);

        BigDecimal profitPercentage = BigDecimal.ZERO;
        if (averageBuyPrice != null && averageBuyPrice.compareTo(BigDecimal.ZERO) > 0) {
            profitPercentage = sellPrice.subtract(averageBuyPrice)
                    .divide(averageBuyPrice, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        return new DeductHoldingResponse(revenue, costBasis, profitOrLoss, profitPercentage, remainingQuantity);
    }

    private void enrichHoldingResponses(List<HoldingResponse> responses) {
        if (responses == null) return;
        for (var response : responses) {
            enrichSingleResponse(response);
        }
    }

    /**
     * Tính toán giá trị hiện tại và Lãi/Lỗ tạm tính (Unrealized PnL) để hiển thị
     */
    private void enrichSingleResponse(HoldingResponse response) {
        if (response == null) return;
        try {
            var coin = coinRepository.getById(response.getCoinId());
            if (coin != null && coin.getPrice() != null) {
                BigDecimal currentPrice = coin.getPrice();
                response.setCurrentPrice(currentPrice);

                BigDecimal quantity = response.getQuantity() != null ? response.getQuantity() : BigDecimal.ZERO;
                BigDecimal totalPrice = quantity.multiply(currentPrice);
                response.setTotalPrice(totalPrice);

                // Tính Lãi/Lỗ tạm tính (Unrealized PnL)
                BigDecimal avgBuyPrice = response.getAverageBuyPrice() != null ? response.getAverageBuyPrice() : BigDecimal.ZERO;
                BigDecimal costBasis = quantity.multiply(avgBuyPrice);
                BigDecimal pnl = totalPrice.subtract(costBasis);
                response.setPnl(pnl);

                BigDecimal pnlPercentage = BigDecimal.ZERO;
                if (avgBuyPrice.compareTo(BigDecimal.ZERO) > 0) {
                    pnlPercentage = currentPrice.subtract(avgBuyPrice)
                            .divide(avgBuyPrice, 4, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal("100"));
                }
                response.setPnlPercentage(pnlPercentage);
            }
        } catch (Exception ignored) {
        }
    }
}
