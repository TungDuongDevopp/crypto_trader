package com.coin.cryptocurrency_trading_management.DTO;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Currency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.WalletStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDTO {
    private String address;
    private WalletStatus status;
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal totalDeposited;
    private BigDecimal totalWithdrawn;
}

