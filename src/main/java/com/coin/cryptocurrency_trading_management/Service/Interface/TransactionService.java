package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.Transaction.TransactionRequestCreate;

import java.io.IOException;

public interface TransactionService {

    void createTransaction(TransactionRequestCreate requestCreate) throws IOException;
}
