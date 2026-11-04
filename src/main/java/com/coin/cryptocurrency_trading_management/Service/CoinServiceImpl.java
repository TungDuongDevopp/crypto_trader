package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Coin.*;
import com.coin.cryptocurrency_trading_management.Repository.Interface.CoinRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.CoinService;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import com.coin.cryptocurrency_trading_management.Util.Mapper.CoinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {
    private final CoinRepository coinRepository;
    private final CoinMapper coinMapper;
    @Override
    public List<CoinResponse> getAll() throws IOException {
       var coins = coinRepository.getAll();
       return coinMapper.toResponseList(coins);
    }

    @Override
    public CoinResponse create(CoinRequestCreate createRequest) throws IOException {
        var coin = coinMapper.toEntity(createRequest);
        coinRepository.save(coin);
        return coinMapper.toResponse(coin);
    }

    @Override
    public CoinResponse changeStatus(CoinChangeStatusRequest statusRequest) throws IOException {
        var coinInDb = coinRepository.getById(statusRequest.getId());
        if(coinInDb == null) {
            throw new ResourceNotFoundException("Coin not found with id: " + statusRequest.getId());
        }
        coinInDb.setStatus(statusRequest.getStatus());
        coinRepository.updateById(coinInDb.getId(),coinInDb);
        return coinMapper.toResponse(coinInDb);
    }

    @Override
    public CoinResponse changePrice(CoinChangePriceRequest priceRequest) throws IOException {
        var coinInDb = coinRepository.getById(priceRequest.getId());
        if(coinInDb == null) {
            throw new ResourceNotFoundException("Coin not found with id: " + priceRequest.getId());
        }
        coinInDb.setPrice(priceRequest.getPrice());
        coinRepository.updateById(coinInDb.getId(),coinInDb);
        return coinMapper.toResponse(coinInDb);
    }

    @Override
    public CoinResponse update(CoinRequestUpdate updateRequest) throws IOException {
        var coinInDb = coinRepository.getById(updateRequest.getId());
        if(coinInDb == null) {
            throw new ResourceNotFoundException("Coin not found with id " + updateRequest.getId());
        }
        if(updateRequest.getName() != null && !updateRequest.getName().isBlank()) {
            coinInDb.setName(updateRequest.getName());
        }
        if(updateRequest.getDescription() != null && !updateRequest.getDescription().isBlank()) {
            coinInDb.setDescription(updateRequest.getDescription());
        }
        coinRepository.updateById(coinInDb.getId(),coinInDb);
        return coinMapper.toResponse(coinInDb);
    }
}
