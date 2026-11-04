package com.coin.cryptocurrency_trading_management.DTO.Coin;

import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinChangeStatusRequest {
    @NotNull
    @Positive(message = "id must greater than zero")
    private long id;
    @NotNull
    private CoinStatus status;
}
