package com.coin.cryptocurrency_trading_management.Entity.Enum;

import lombok.Getter;

@Getter
public enum WalletStatus {
    ACTIVE(0),
    FROZEN(1),
    CLOSED(2);
    private final int value;
    WalletStatus(int value) {
        this.value = value;
    }
    public static WalletStatus fromValue(int value) {
        for (WalletStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status: " + value);
    }
}
