package com.coin.cryptocurrency_trading_management.DTO.Wallet;

import com.coin.cryptocurrency_trading_management.Entity.Enum.WalletStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WalletChangeStatusRequest {
    @Positive
    private long id;
    @NotNull
    private WalletStatus status;
}
