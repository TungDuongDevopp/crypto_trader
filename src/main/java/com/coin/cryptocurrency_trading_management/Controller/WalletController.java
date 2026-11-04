package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.WalletDTO;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class WalletController {

    private final AuthService authService;
    private final WalletService walletService;

    public WalletDTO getWalletInfo() {
        try {
            User user = authService.getCurrentUser();
            if (user == null) {
                return null;
            }
            Wallet wallet = walletService.getWalletByUserId(user.getId());
            if (wallet == null) {
                return null;
            }
            BigDecimal balance = wallet.getBalance() != null ? wallet.getBalance() : BigDecimal.ZERO;

            return WalletDTO.builder()
                    .address(wallet.getAddress())
                    .status(wallet.getStatus())
                    .currency(wallet.getCurrency())
                    .balance(balance)
                    .totalDeposited(balance) // Mặc định số tiền ban đầu (hoặc sau này tổng hợp từ Transaction)
                    .totalWithdrawn(BigDecimal.ZERO)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
