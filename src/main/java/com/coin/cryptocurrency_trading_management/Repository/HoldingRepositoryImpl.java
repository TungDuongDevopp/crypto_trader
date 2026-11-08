package com.coin.cryptocurrency_trading_management.Repository;

import com.coin.cryptocurrency_trading_management.Entity.Holding;
import com.coin.cryptocurrency_trading_management.Repository.Interface.HoldingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class HoldingRepositoryImpl extends FileRepositoryImpl<Holding> implements HoldingRepository {
    public HoldingRepositoryImpl(@Value("${app.data.dir}") String dataDir, ObjectMapper objectMapper) {
        super(dataDir, "holdings.json", Holding.class, objectMapper);
    }

    @Override
    public Holding getHoldingByUserId(long userId) throws IOException {
        return getAll().stream()
                .filter(x -> x.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Holding getHoldingByCoinId(long coinId) throws IOException {
        return getAll().stream()
                .filter(x -> x.getCoinId() == coinId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Holding getHoldingByUserIdAndCoinId(long userId, long coinId) throws IOException {
        return getAll().stream()
                .filter(x -> x.getUserId() == userId && x.getCoinId() == coinId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Holding> getHoldingsByUserId(long userId) throws IOException {
        return getAll().stream()
                .filter(x -> x.getUserId() == userId)
                .toList();
    }
}
