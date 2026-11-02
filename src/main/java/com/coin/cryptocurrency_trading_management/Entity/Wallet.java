package com.coin.cryptocurrency_trading_management.Entity;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Currency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.WalletStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

@AllArgsConstructor
public class Wallet implements Identifiable {

    private long id;
    private String address;
    private BigDecimal balance;
    private long userId;
    private Currency currency;
    private WalletStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public Wallet(){
        createdAt = LocalDateTime.now();
        status = WalletStatus.ACTIVE;
    }
}
