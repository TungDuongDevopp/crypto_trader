package com.coin.cryptocurrency_trading_management.Entity.Enum;

public enum TransactionStatus {
    PENDING(0),
    SUCCESS(1),
    FAILED(2);
    private final int value;
    private TransactionStatus(int value) {
        this.value = value;
    }
    public static TransactionStatus fromValue(int value) {
        for (TransactionStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Transaction status: " + value);
    }
}
