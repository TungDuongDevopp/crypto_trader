package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.Order.OrderRequestCreate;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.OrderService;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    public void buyCoin(OrderRequestCreate request) throws IOException {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new ResourceNotFoundException("User not logged in");
        }
        orderService.buyCoin(user.getId(), request);
    }

    public void sellCoin(OrderRequestCreate request) throws IOException {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new ResourceNotFoundException("User not logged in");
        }
        orderService.sellCoin(user.getId(), request);
    }
}

