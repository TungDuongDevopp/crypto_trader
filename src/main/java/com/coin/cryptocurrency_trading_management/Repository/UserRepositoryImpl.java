package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Repository.Interface.UserRepository;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class UserRepositoryImpl extends FileRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl(@Value("${app.data.dir}") String dataDir, ObjectMapper objectMapper) {
        super(dataDir, "users.json", User.class, objectMapper);
    }

    @Override
    public User getUserByUsername(String username) throws IOException {
        return getAll().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException(("User not found with username:"+ username)));
    }
}
