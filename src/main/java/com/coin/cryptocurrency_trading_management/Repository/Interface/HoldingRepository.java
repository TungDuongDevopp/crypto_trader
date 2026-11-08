package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Holding;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface HoldingRepository extends FileRepository<Holding> {

    Holding getHoldingByUserId(long userId) throws IOException;

    Holding getHoldingByCoinId(long coinId) throws IOException;

    Holding getHoldingByUserIdAndCoinId(long userId, long coinId) throws IOException;

    List<Holding> getHoldingsByUserId(long userId) throws IOException;
}
