package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.Entity.Session;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    public void login(String userName, String password) throws IOException {
        authService.login(userName, password);
    }

    public void register(String username, String password, String passwordAgain) throws IOException {
        authService.register(username, password, passwordAgain);
    }

    public void logout() throws IOException {
        authService.logout();
    }
    public void changePassword(String oldPassword, String newPassword) throws IOException {
        authService.changePassword(oldPassword, newPassword);
    }

    public Session getCurrentSession() throws IOException {
        return authService.getCurrentSession();
    }

    public Session getActiveCurrentSession() throws IOException {
        return authService.getActiveCurrentSession();
    }

    public boolean hasActiveSession() throws IOException {
        return authService.getActiveCurrentSession() != null;
    }

    public User getCurrentUser() throws IOException {
        return authService.getCurrentUser();
    }
}
