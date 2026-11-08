package com.coin.cryptocurrency_trading_management.DTO.Coin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class CoinChangePriceRequest {
    @Positive(message = "id must greater than zero")
    private long id;

    @Positive(message = "price must greater than zero")
    @NotNull
    private BigDecimal price;
}
