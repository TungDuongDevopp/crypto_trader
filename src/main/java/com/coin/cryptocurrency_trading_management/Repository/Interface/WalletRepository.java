package com.coin.cryptocurrency_trading_management.Repository.Interface;

import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends FileRepository<Wallet>{
}
