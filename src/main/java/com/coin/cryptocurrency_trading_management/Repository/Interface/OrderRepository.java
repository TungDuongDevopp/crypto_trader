package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Order;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface OrderRepository extends  FileRepository<Order> {

    List<Order> getAllByUserId(long userId) throws IOException;
}
