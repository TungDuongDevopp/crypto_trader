package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Transaction;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
@Repository
public interface TransactionRepository extends FileRepository<Transaction>{
    List<Transaction> findAllTransactionByOrderId(long orderId) throws IOException;
}
