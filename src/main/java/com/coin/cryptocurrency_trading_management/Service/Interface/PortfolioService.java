package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.PortfolioDTO;

import java.io.IOException;

public interface PortfolioService {

    PortfolioDTO getPortfolio(long userId) throws IOException;
}
