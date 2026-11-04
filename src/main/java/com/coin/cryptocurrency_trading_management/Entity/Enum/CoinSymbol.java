package com.coin.cryptocurrency_trading_management.Entity.Enum;

import lombok.Getter;

@Getter
public enum CoinSymbol {
    BTC(1),
    ETH(2),
    BNB(3),
    SOL(4),
    BCH(5),
    XRP(6),
    LTC(7),
    DOGE(8),
    AVAX(9),
    LINK(10),
    DOT(11);
    private final int value;
    CoinSymbol(int value) {
        this.value = value;
    }
    public static CoinSymbol fromValue(int value) {
        for (CoinSymbol symbol : values()) {
            if (symbol.value == value) {
                return symbol;
            }
        }
        throw new IllegalArgumentException("Invalid CoinSymbol: " + value);
    }
}
