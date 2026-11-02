package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import com.coin.cryptocurrency_trading_management.Repository.Interface.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

public class WalletRepositoryImpl extends FileRepositoryImpl<Wallet> implements WalletRepository {
    public WalletRepositoryImpl(@Value("${app.data.dir}") String dataDir,ObjectMapper objectMapper) {
        super(dataDir, "wallets.json", Wallet.class, objectMapper);
    }
}
