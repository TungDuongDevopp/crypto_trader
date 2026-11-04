package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.User.UserResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserResponse> getAll() throws IOException;
}
