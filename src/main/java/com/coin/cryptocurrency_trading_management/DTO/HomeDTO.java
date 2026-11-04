package com.coin.cryptocurrency_trading_management.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeDTO {
    private String username;
    private BigDecimal walletBalance;
    private BigDecimal portfolioValue;
    private BigDecimal totalAssets;
}

