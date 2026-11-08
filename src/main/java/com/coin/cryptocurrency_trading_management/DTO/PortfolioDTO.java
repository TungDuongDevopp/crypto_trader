package com.coin.cryptocurrency_trading_management.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class PortfolioDTO {
    private String username;
    private BigDecimal walletBalance;    // Tiền mặt còn trong ví
    private BigDecimal investedValue;    // Tổng giá trị coin theo giá thị trường (SUM totalPrice)
    private BigDecimal costBasis;        // Tổng tiền vốn đã bỏ ra (SUM qty × avgBuyPrice)
    private BigDecimal totalAssets;      // walletBalance + investedValue
    private BigDecimal unrealizedPnl;    // investedValue - costBasis
    private BigDecimal unrealizedPnlPct; // (unrealizedPnl / costBasis) * 100
}
