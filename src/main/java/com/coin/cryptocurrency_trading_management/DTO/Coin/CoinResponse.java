package com.coin.cryptocurrency_trading_management.DTO.Coin;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinCurrency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinStatus;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinSymbol;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CoinResponse {
    private long id;
    private String name;
    private CoinCurrency currency;
    private CoinStatus status;
    private CoinSymbol symbol;
    private BigDecimal price;
}
