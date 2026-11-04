package com.coin.cryptocurrency_trading_management.Entity.Enum;

import lombok.Getter;

@Getter
public enum CoinCurrency {
    USDT(0),
    USDC(1),
    BTC(2),
    ETH(3),
    BNB(4);
    private final int value;
    CoinCurrency(int value) {
        this.value = value;
    }
    public static CoinCurrency fromValue(int value) {
        for (CoinCurrency currency : values()) {
            if (currency.value == value) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Invalid CoinCurrency: " + value);
    }
}
