package com.coin.cryptocurrency_trading_management.Entity.Enum;

import lombok.Getter;

@Getter
public enum OrderType {
    Market (0),
    Limit (1),
    Future (2);

    private final int value;
    OrderType(int value) {
        this.value = value;
    }
    public static OrderType fromValue(int value) {
        for (OrderType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid OrderType: " + value);
    }
}
