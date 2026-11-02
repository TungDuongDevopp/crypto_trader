package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.User;

import java.io.IOException;

public interface UserRepository extends FileRepository<User> {
    User getUserByUsername(String username) throws IOException;
}
