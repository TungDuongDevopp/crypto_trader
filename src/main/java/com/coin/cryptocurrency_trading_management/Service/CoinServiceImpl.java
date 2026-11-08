package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Coin.*;
import com.coin.cryptocurrency_trading_management.Entity.Coin;
import com.coin.cryptocurrency_trading_management.Entity.Enum.CoinStatus;
import com.coin.cryptocurrency_trading_management.Repository.Interface.CoinRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.CoinService;
import com.coin.cryptocurrency_trading_management.Util.Exception.CanMaltipulateDataException;
import com.coin.cryptocurrency_trading_management.Util.Exception.DuplicateResourceException;
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
    public CoinResponse getById(long id) throws IOException {
        var coin = coinRepository.getById(id);
        if (coin == null) {
            throw new ResourceNotFoundException("Coin not found with id: " + id);
        }
        return coinMapper.toResponse(coin);
    }
    private void checkAccess(Coin coin){
        if(coin.getStatus() == CoinStatus.DELISTED){
            throw new CanMaltipulateDataException("Can Maltipulate Data");
        }
    }

    @Override
    public CoinResponse create(CoinRequestCreate createRequest) throws IOException {
        var existCoin = coinRepository.existByCoinSymbol(createRequest.getSymbol());
        if (existCoin) {
            throw new DuplicateResourceException("Coin with Symbol " + createRequest.getSymbol() + " already exists");
        }
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
        checkAccess(coinInDb);
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
        checkAccess(coinInDb);
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

        checkAccess(coinInDb);
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
