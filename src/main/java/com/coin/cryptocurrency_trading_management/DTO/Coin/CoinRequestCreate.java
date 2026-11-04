package com.coin.cryptocurrency_trading_management.DTO.Coin;

import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinCurrency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinSymbol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CoinRequestCreate {

    @NotBlank(message = "Name can't not be null")
    private String name;

    @Positive(message = "Price must greater than zero")
    private BigDecimal price;

    @NotNull(message = "Coin symbol can not be null")
    private CoinSymbol symbol;

    @NotNull(message = "Coin currency can not be null")
    private CoinCurrency currency;

    private String description;
}
