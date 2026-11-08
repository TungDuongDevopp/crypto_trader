package com.coin.cryptocurrency_trading_management.Entity;

import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderStatus;
import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderType;
import com.coin.cryptocurrency_trading_management.Entity.Enum.Side;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Order extends BaseEntity {
    private long userId;
    private long coinId;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private BigDecimal quantity;      // Số lượng coin muốn mua/bán
    private BigDecimal price;         // Giá đặt (null nếu Market, có giá nếu Limit)
    private BigDecimal filledPrice;   // Giá thực tế khi khớp lệnh
    private Side side;

    public Order(){
        super();
        orderStatus = OrderStatus.NEW;
    }
}
