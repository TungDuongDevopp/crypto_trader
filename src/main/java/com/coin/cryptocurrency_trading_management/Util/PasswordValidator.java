package com.coin.cryptocurrency_trading_management.Util;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"
    );

    public static boolean isValid(String password) {
        return password != null
                && PASSWORD_PATTERN.matcher(password).matches();
    }
}
