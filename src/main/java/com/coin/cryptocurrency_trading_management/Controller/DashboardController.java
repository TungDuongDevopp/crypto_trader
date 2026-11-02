package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final AuthService authService;

    public String getUserName() {
        try {
            User currentUser = authService.getCurrentUser();
            return currentUser != null ? currentUser.getUsername() : "Vô danh";
        } catch (IOException e) {
            return "Vô danh";
        }
    }

    public boolean logout() {
        try {
            authService.logout();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
