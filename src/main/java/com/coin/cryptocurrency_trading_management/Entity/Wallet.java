package com.coin.cryptocurrency_trading_management.Entity;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Currency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.WalletStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter

@AllArgsConstructor
public class Wallet extends BaseEntity {

    private String address;
    private BigDecimal balance;
    private long userId;
    private Currency currency;
    private WalletStatus status;
    public Wallet(){
        super();
        status = WalletStatus.ACTIVE;
    }
}
