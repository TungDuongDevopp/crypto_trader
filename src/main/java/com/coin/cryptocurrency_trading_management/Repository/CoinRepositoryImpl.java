package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.Coin;
import com.coin.cryptocurrency_trading_management.Repository.Interface.CoinRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CoinRepositoryImpl extends FileRepositoryImpl<Coin> implements CoinRepository {
    public CoinRepositoryImpl(@Value("${app.data.dir}") String dataDir,ObjectMapper objectMapper) {
        super(dataDir, "coins.json", Coin.class, objectMapper);
    }
}
