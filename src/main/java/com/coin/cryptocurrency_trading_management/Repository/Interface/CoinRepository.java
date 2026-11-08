package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Coin;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinSymbol;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface CoinRepository extends FileRepository<Coin> {
    boolean existByCoinSymbol(CoinSymbol coinSymbol) throws IOException;
}
