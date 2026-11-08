package com.coin.cryptocurrency_trading_management.Entity.Enum;

public enum OrderStatus {
    NEW(0),
    PENDING_NEW(1),
    PARTIALLY_FILLED(2),
    FILLED(3),
    CANCELED(4),
    EXPIRED(5),
    REJECTED(6);
    private final int value;
    private OrderStatus(int value) {
        this.value = value;
    }
    public static OrderStatus fromValue(int value) {
        for (OrderStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Order satus: " + value);
    }
}
