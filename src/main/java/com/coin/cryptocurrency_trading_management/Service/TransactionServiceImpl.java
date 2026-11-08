package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Transaction.TransactionRequestCreate;
import com.coin.cryptocurrency_trading_management.Entity.Transaction;
import com.coin.cryptocurrency_trading_management.Repository.Interface.OrderRepository;
import com.coin.cryptocurrency_trading_management.Repository.Interface.TransactionRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.TransactionService;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;

    @Override
    public void createTransaction(TransactionRequestCreate requestCreate) throws IOException {
        var order = orderRepository.getById(requestCreate.getOrderId());
        if(order == null){
            throw new ResourceNotFoundException("Order Not Found");
        }
        var transaction = new Transaction();
        transaction.setOrderId(requestCreate.getOrderId());
        transaction.setExecutionPrice(requestCreate.getExecutionPrice());
        transaction.setQuantity(requestCreate.getQuantity());
        transactionRepository.save(transaction);
    }
}
