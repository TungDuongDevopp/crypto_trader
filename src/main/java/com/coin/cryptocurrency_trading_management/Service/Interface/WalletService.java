package com.coin.cryptocurrency_trading_management.Service.Interface;

import com.coin.cryptocurrency_trading_management.DTO.Wallet.AdminWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.UserWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.WalletChangeStatusRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    void createWallet(long userId) throws IOException;
    UserWalletResponse getWalletByUserId(long userId) throws IOException;
    AdminWalletResponse changeStatus(WalletChangeStatusRequest walletChangeStatusRequest) throws IOException;
    List<AdminWalletResponse> getAll() throws IOException;

    /** Trừ tiền ví (khi mua coin). Throw BusinessException nếu không đủ số dư. */
    void deductBalance(long userId, BigDecimal amount) throws IOException;

    /** Cộng tiền ví (khi bán coin / nhận tiền). */
    void addBalance(long userId, BigDecimal amount) throws IOException;
}
