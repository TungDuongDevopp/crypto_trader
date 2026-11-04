package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Coin;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends FileRepository<Coin> {
}
