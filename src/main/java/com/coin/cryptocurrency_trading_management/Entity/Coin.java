package com.coin.cryptocurrency_trading_management.Entity;

import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinCurrency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinStatus;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinSymbol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
public class Coin extends BaseEntity {
    private String name;
    private CoinCurrency currency;
    private CoinStatus status;
    private CoinSymbol symbol;
    private BigDecimal price;
    private String description;

    public Coin (){
    super();
    status = CoinStatus.ACTIVE;
    }
}

