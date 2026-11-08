package com.coin.cryptocurrency_trading_management.Util.Mapper;

import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;
import com.coin.cryptocurrency_trading_management.Entity.Holding;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HoldingMapper {
    @Mapping(target = "currentPrice", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "pnl", ignore = true)
    @Mapping(target = "pnlPercentage", ignore = true)
    HoldingResponse toResponse(Holding holding);

    List<HoldingResponse> toResponseList(List<Holding> holdings);
}
