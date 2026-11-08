package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.Coin.*;
import com.coin.cryptocurrency_trading_management.Service.Interface.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CoinController {
    private final CoinService coinService;

    public List<CoinResponse> getCoins() throws IOException {
        return coinService.getAll();
    }
    public CoinResponse getCoinById(long id) throws IOException {
        return coinService.getById(id);
    }
    public CoinResponse createCoin(CoinRequestCreate coinRequestCreate) throws IOException {
        return coinService.create(coinRequestCreate);
    }
    public CoinResponse updateCoin(CoinRequestUpdate coinRequestUpdate) throws IOException {
        return coinService.update(coinRequestUpdate);
    }
    public CoinResponse updateCoinPrice(CoinChangePriceRequest coinChangePriceRequest) throws IOException {
        return coinService.changePrice(coinChangePriceRequest);
    }
    public CoinResponse updateCoinStatus( CoinChangeStatusRequest coinChangeStatusRequest) throws IOException {
        return coinService.changeStatus(coinChangeStatusRequest);
    }

}
