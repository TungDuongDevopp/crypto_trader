package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.DashboardDTO;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Repository.Interface.CoinRepository;
import com.coin.cryptocurrency_trading_management.Repository.Interface.SessionRepository;
import com.coin.cryptocurrency_trading_management.Repository.Interface.UserRepository;
import com.coin.cryptocurrency_trading_management.Repository.Interface.WalletRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final CoinRepository coinRepository;
    private final SessionRepository sessionRepository;
    private final WalletRepository walletRepository;
    private final AuthService authService;
    public DashboardDTO getDashboardInfo() throws IOException {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new BusinessException("User is not logged in");
        }
        var numberOfUser = userRepository.getAll().size();
        var numberOfCoins = coinRepository.getAll().size();
        var numberOfSessions = sessionRepository.getAll().size();
        var numberOfWallets = walletRepository.getAll().size();
        return DashboardDTO.builder()
                .username(user.getUsername())
                .numberOfCoin(numberOfCoins)
                .numberOfSession(numberOfSessions)
                .numberOfWallet(numberOfWallets)
                .numberOfUser(numberOfUser)
                .build();
    }
    public void logout() throws IOException {
        authService.logout();
    }

}
