package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Session;
import com.coin.cryptocurrency_trading_management.Entity.User;

import java.io.IOException;

public interface AuthService {
    void register(String username, String password, String againPassword) throws IOException;

    Session login(String username, String password) throws IOException;

    void logout() throws IOException;

    void changePassword(String oldPassword, String newPassword) throws IOException;

    Session getCurrentSession() throws IOException;

    Session getActiveCurrentSession() throws IOException;

    User getCurrentUser() throws IOException;
}
