package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.Coin.*;
import com.coin.cryptocurrency_trading_management.Entity.Coin;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface CoinService {
    List<CoinResponse> getAll() throws IOException;

    CoinResponse getById(long id) throws IOException;

    CoinResponse create(CoinRequestCreate createRequest) throws IOException;

    CoinResponse changeStatus(CoinChangeStatusRequest statusRequest) throws IOException;

    CoinResponse changePrice (CoinChangePriceRequest priceRequest) throws IOException;

    CoinResponse update(CoinRequestUpdate updateRequest) throws IOException;
}
