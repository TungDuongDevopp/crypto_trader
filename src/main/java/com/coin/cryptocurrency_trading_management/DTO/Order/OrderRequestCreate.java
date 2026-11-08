package com.coin.cryptocurrency_trading_management.DTO.Order;

import com.coin.cryptocurrency_trading_management.Entity.Enum.OrderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class OrderRequestCreate {
    @Positive
    private long coinId;
    @NotNull
    private OrderType orderType;
    @Positive
    @NotNull
    private BigDecimal quantity;      // Số lượng coin muốn mua/bán
    @Positive
    private BigDecimal price;         // null nếu Market, có giá nếu Limit
}
