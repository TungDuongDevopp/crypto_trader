package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinChangePriceRequest;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinChangeStatusRequest;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinRequestCreate;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinRequestUpdate;
import com.coin.cryptocurrency_trading_management.Entity.Coin;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface CoinService {
    List<Coin> getAll() throws IOException;

    void create(CoinRequestCreate createRequest);

    boolean changeStatus(CoinChangeStatusRequest statusRequest);

    void changePrice (CoinChangePriceRequest priceRequest);

    void update(CoinRequestUpdate updateRequest);
}
