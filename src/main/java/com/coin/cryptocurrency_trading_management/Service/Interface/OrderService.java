package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.Order.OrderRequestCreate;

import java.io.IOException;

public interface OrderService {
    void buyCoin(long userId, OrderRequestCreate request) throws IOException;
    void sellCoin(long userId, OrderRequestCreate request) throws IOException;
    void orderMatch(long orderId) throws IOException;
}
