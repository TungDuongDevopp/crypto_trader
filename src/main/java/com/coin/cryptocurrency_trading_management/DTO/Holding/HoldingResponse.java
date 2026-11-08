package com.coin.cryptocurrency_trading_management.DTO.Holding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoldingResponse {
    private long id;
    private long userId;
    private long coinId;
    private BigDecimal quantity;
    private BigDecimal averageBuyPrice;
    private BigDecimal currentPrice;
    private BigDecimal totalPrice;
    private BigDecimal pnl;           // Lãi/lỗ tạm tính ($) = (currentPrice - averageBuyPrice) * quantity
    private BigDecimal pnlPercentage; // Tỷ lệ % lãi/lỗ = (currentPrice - averageBuyPrice) / averageBuyPrice * 100
}
