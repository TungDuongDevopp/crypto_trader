package com.coin.cryptocurrency_trading_management.Util.Mapper;

import com.coin.cryptocurrency_trading_management.DTO.Order.OrderResponse;
import com.coin.cryptocurrency_trading_management.Entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toResponse(Order order);
    List<OrderResponse> toResponseList(List<Order> orders);
}
