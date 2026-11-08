package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.PortfolioDTO;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.PortfolioService;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AuthService authService;

    public PortfolioDTO getPortfolio() throws IOException {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new ResourceNotFoundException("User not logged in");
        }
        return portfolioService.getPortfolio(user.getId());
    }
}

