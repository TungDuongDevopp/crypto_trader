package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Wallet;

import java.io.IOException;

public interface WalletService {
    public Wallet createWallet(long userId) throws IOException;

}
