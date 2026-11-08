package com.coin.cryptocurrency_trading_management.DTO.Order;

import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderStatus;
import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderType;
import com.coin.cryptocurrency_trading_management.Entity.Enum.Side;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderResponse {
    private long id;
    private long userId;
    private long coinId;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal filledPrice;
    private Side side;
}
