package com.coin.cryptocurrency_trading_management.Util;

public class UsernameValidator {
    public static boolean isValid(String username) {
        return username != null
                && username.length() > 4
                && !username.isBlank();
    }
}
