package com.coin.cryptocurrency_trading_management.DTO.Wallet;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Currency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.WalletStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletResponse {
    private String address;
    private WalletStatus status;
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal totalDeposited;
    private BigDecimal totalWithdrawn;
}

