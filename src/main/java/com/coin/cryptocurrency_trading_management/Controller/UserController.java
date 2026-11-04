package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.User.UserResponse;
import com.coin.cryptocurrency_trading_management.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public List<UserResponse> getUsers() throws IOException {
        return userService.getAll();
    }
}
