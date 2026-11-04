package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Wallet;

import java.io.IOException;

public interface WalletService {
    void createWallet(long userId) throws IOException;
    Wallet getWalletByUserId(long userId) throws IOException;
}
