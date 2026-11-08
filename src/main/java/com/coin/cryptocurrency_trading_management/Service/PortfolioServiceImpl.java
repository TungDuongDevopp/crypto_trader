package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;
import com.coin.cryptocurrency_trading_management.DTO.PortfolioDTO;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.HoldingService;
import com.coin.cryptocurrency_trading_management.Service.Interface.PortfolioService;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final WalletService walletService;
    private final HoldingService holdingService;
    private final AuthService authService;

    @Override
    public PortfolioDTO getPortfolio(long userId) throws IOException {
        // 1. Lấy thông tin user
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // 2. Wallet balance (tiền mặt USDT còn lại)
        var walletResponse = walletService.getWalletByUserId(userId);
        BigDecimal walletBalance = (walletResponse != null && walletResponse.getBalance() != null)
                ? walletResponse.getBalance()
                : BigDecimal.ZERO;

        // 3. Lấy tất cả holding của user (đã được enrich với currentPrice, totalPrice, pnl)
        List<HoldingResponse> holdings = holdingService.getHoldingsByUserId(userId);

        // 4. investedValue = SUM(holding.totalPrice)  — giá trị coin theo giá thị trường
        BigDecimal investedValue = holdings.stream()
                .map(h -> h.getTotalPrice() != null ? h.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 5. costBasis = SUM(quantity × averageBuyPrice) — tổng tiền vốn đã bỏ ra
        BigDecimal costBasis = holdings.stream()
                .map(h -> {
                    BigDecimal qty = h.getQuantity() != null ? h.getQuantity() : BigDecimal.ZERO;
                    BigDecimal avg = h.getAverageBuyPrice() != null ? h.getAverageBuyPrice() : BigDecimal.ZERO;
                    return qty.multiply(avg);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 6. totalAssets = walletBalance + investedValue
        BigDecimal totalAssets = walletBalance.add(investedValue);

        // 7. unrealizedPnl ($) = investedValue - costBasis
        BigDecimal unrealizedPnl = investedValue.subtract(costBasis);

        // 8. unrealizedPnlPct (%) = (unrealizedPnl / costBasis) × 100
        BigDecimal unrealizedPnlPct = BigDecimal.ZERO;
        if (costBasis.compareTo(BigDecimal.ZERO) > 0) {
            unrealizedPnlPct = unrealizedPnl
                    .divide(costBasis, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        // 9. Đóng gói kết quả
        PortfolioDTO dto = new PortfolioDTO();
        dto.setUsername(user.getUsername());
        dto.setWalletBalance(walletBalance);
        dto.setInvestedValue(investedValue);
        dto.setCostBasis(costBasis);
        dto.setTotalAssets(totalAssets);
        dto.setUnrealizedPnl(unrealizedPnl);
        dto.setUnrealizedPnlPct(unrealizedPnlPct);
        return dto;
    }
}
