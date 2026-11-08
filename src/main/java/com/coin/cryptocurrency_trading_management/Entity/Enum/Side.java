package com.coin.cryptocurrency_trading_management.Entity.Enum;

public enum Side {
    LONG(0),
    SHORT(1);
    private final int value;
    Side(int value) {
        this.value = value;
    }
    public static Side fromValue(int value) {
        for (Side side : values()) {
            if (side.value == value) {
                return side;
            }
        }
        throw new IllegalArgumentException("Invalid side: " + value);
    }
}
