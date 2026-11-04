package com.coin.cryptocurrency_trading_management.DTO.Coin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CoinChangePriceRequest {
    @NotNull
    @Positive(message = "id must greater than zero")
    private long id;

    @Positive(message = "price must greater than zero")
    private BigDecimal price;
}
