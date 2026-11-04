package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.HomeDTO;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final AuthService authService;
    private final WalletService walletService;

    public HomeDTO getDashboardInfo() throws IOException {
            User user = authService.getCurrentUser();
            if (user == null) {
                throw new BusinessException("User is not logged in");
            }
            Wallet wallet = walletService.getWalletByUserId(user.getId());
            BigDecimal walletBalance = (wallet != null && wallet.getBalance() != null)
                    ? wallet.getBalance()
                    : BigDecimal.ZERO;
            BigDecimal portfolioValue = BigDecimal.ZERO;
            BigDecimal totalAssets = walletBalance.add(portfolioValue);

            return HomeDTO.builder()
                    .username(user.getUsername())
                    .walletBalance(walletBalance)
                    .portfolioValue(portfolioValue)
                    .totalAssets(totalAssets)
                    .build();

    }
    public void logout() throws IOException {
        authService.logout();
    }
}
