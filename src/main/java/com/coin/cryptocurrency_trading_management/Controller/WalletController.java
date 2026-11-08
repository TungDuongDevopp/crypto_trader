package com.coin.cryptocurrency_trading_management.Controller;

import com.coin.cryptocurrency_trading_management.DTO.Wallet.AdminWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.UserWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.WalletChangeStatusRequest;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WalletController {

    private final AuthService authService;
    private final WalletService walletService;

    public UserWalletResponse getWalletInfo() {
        try {
            User user = authService.getCurrentUser();
            if (user == null) {
                return null;
            }
            return walletService.getWalletByUserId(user.getId());
        } catch (Exception e) {
            return null;
        }
    }

    public List<AdminWalletResponse> getWallets() throws IOException {
        return walletService.getAll();
    }

    public AdminWalletResponse changeStatus(WalletChangeStatusRequest walletChangeStatusRequest) throws IOException {
        return walletService.changeStatus(walletChangeStatusRequest);
    }
}
