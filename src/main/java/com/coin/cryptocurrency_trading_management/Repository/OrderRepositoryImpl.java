package com.coin.cryptocurrency_trading_management.Repository;
import com.coin.cryptocurrency_trading_management.Entity.Order;

import com.coin.cryptocurrency_trading_management.Repository.Interface.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class OrderRepositoryImpl  extends FileRepositoryImpl<Order>  implements OrderRepository {
    public OrderRepositoryImpl(@Value("${app.data.dir}") String dataDir, ObjectMapper objectMapper) {
        super(dataDir, "orders.json", Order.class, objectMapper);
    }

    @Override
    public List<Order> getAllByUserId(long userId) throws IOException {
        return getAll().stream().filter(x->x.getUserId() == userId).toList();
    }
}
