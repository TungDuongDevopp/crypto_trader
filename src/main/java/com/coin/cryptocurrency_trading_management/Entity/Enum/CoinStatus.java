package com.coin.cryptocurrency_trading_management.Entity.Enum;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum CoinStatus {
    ACTIVE(0),
    INACTIVE(1),
    DELISTED(2);

    private final int value;

    CoinStatus(int value) {
        this.value = value;
    }

    public static CoinStatus fromValue(int value) {
        for (CoinStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid CoinStatus: " + value);
    }

}