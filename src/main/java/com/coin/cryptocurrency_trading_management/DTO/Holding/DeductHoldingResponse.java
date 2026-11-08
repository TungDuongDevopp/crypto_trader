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
public class DeductHoldingResponse {
    private BigDecimal revenue;          // Tổng tiền thu về (quantity * price)
    private BigDecimal costBasis;        // Giá vốn gốc (quantity * averageBuyPrice)
    private BigDecimal profitOrLoss;      // Lợi nhuận lãi/lỗ ($) = revenue - costBasis
    private BigDecimal profitPercentage;  // Tỷ lệ % lời/lỗ
    private BigDecimal remainingQuantity; // Số lượng coin còn lại trong ví
}

