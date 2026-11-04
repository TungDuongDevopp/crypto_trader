package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import com.coin.cryptocurrency_trading_management.Repository.Interface.WalletRepository;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class WalletRepositoryImpl extends FileRepositoryImpl<Wallet> implements WalletRepository {
    public WalletRepositoryImpl(@Value("${app.data.dir}") String dataDir,ObjectMapper objectMapper) {
        super(dataDir, "wallets.json", Wallet.class, objectMapper);
    }
    @Override
    public Wallet getWalletByUserId(Long userId) throws IOException {
        return getAll().stream()
                .filter(wallet-> wallet.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(("Wallet not found with userId:"+ userId)));
    }
}
