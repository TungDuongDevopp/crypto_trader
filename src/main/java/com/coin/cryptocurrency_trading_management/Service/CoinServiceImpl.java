package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinChangePriceRequest;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinChangeStatusRequest;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinRequestCreate;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinRequestUpdate;
import com.coin.cryptocurrency_trading_management.Entity.Coin;
import com.coin.cryptocurrency_trading_management.Repository.Interface.CoinRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {
    private final CoinRepository coinRepository;
    @Override
    public List<Coin> getAll() throws IOException {
        return coinRepository.getAll();
    }

    @Override
    public void create(CoinRequestCreate createRequest) {

    }

    @Override
    public boolean changeStatus(CoinChangeStatusRequest statusRequest) {
        return false;
    }

    @Override
    public void changePrice(CoinChangePriceRequest priceRequest) {

    }

    @Override
    public void update(CoinRequestUpdate updateRequest) {

    }
}
