package com.coin.cryptocurrency_trading_management.Util.Mapper;

import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinResponse;
import com.coin.cryptocurrency_trading_management.DTO.Coin.CoinRequestCreate;
import com.coin.cryptocurrency_trading_management.Entity.Coin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoinMapper {
    Coin toEntity(CoinRequestCreate coinRequestCreate);
    CoinResponse toResponse(Coin entity);
    List<CoinResponse> toResponseList(List<Coin> entity);
}
