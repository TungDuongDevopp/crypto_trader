package com.coin.cryptocurrency_trading_management;

import com.coin.cryptocurrency_trading_management.Util.UsernameValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsernameValidatorTest {
    @Test
    void shouldAcceptValidUsername() {
        assertTrue(UsernameValidator.isValid("admin01"));
    }

    @Test
    void shouldRejectShortUsername() {
        assertFalse(UsernameValidator.isValid("vy"));
    }

    @Test
    void shouldRejectBlankUsername() {
        assertFalse(UsernameValidator.isValid("       "));
    }

    @Test
    void shouldRejectNullUsername() {
        assertFalse(UsernameValidator.isValid(null));
    }
}
