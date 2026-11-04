package com.coin.cryptocurrency_trading_management.DTO.Coin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CoinRequestUpdate {
    @NotNull
    @Positive(message = "Id must greater than zero")
    private long id;
    private String name;
    private String description;

}
