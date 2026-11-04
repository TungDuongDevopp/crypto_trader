package com.coin.cryptocurrency_trading_management.DTO.Coin;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinCurrency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinStatus;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinSymbol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoinResponse {
    private long id;
    private String name;
    private CoinCurrency currency;
    private CoinStatus status;
    private CoinSymbol symbol;
    private BigDecimal price;
}
