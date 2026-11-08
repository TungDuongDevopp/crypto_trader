package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.Holding.AddHoldingRequest;
import com.coin.cryptocurrency_trading_management.DTO.Holding.DeductHoldingRequest;
import com.coin.cryptocurrency_trading_management.DTO.Holding.DeductHoldingResponse;
import com.coin.cryptocurrency_trading_management.DTO.Holding.HoldingResponse;

import java.io.IOException;
import java.util.List;

public interface HoldingService {

    List<HoldingResponse> getAll() throws IOException;

    List<HoldingResponse> getHoldingsByUserId(long userId) throws IOException;

    HoldingResponse getHoldingByUserIdAndCoinId(long userId, long coinId) throws IOException;

    void addHolding(AddHoldingRequest request) throws IOException;

    DeductHoldingResponse deductHolding(DeductHoldingRequest request) throws IOException;

}
