package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.Transaction;
import com.coin.cryptocurrency_trading_management.Repository.Interface.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class TransactionRepositoryImpl extends FileRepositoryImpl<Transaction> implements TransactionRepository {
    public TransactionRepositoryImpl(@Value("${app.data.dir}")  String dataDir, ObjectMapper objectMapper) {
        super(dataDir, "transactions.json", Transaction.class, objectMapper);
    }

    @Override
    public List<Transaction> findAllTransactionByOrderId(long orderId) throws IOException {
        return getAll().stream().filter(x->x.getOrderId() == orderId).toList();
    }
}
