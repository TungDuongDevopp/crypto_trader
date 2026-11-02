package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Currency;
import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import com.coin.cryptocurrency_trading_management.Repository.Interface.WalletRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private String generateWalletAddress() {
        byte[] bytes = new byte[16];
        SECURE_RANDOM.nextBytes(bytes);

        return "WLT-" + HexFormat.of().withUpperCase().formatHex(bytes);
    }

    @Override
    public Wallet createWallet(long userId) throws IOException {
        var wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAddress(generateWalletAddress());
        wallet.setBalance(new BigDecimal("300"));
        wallet.setCurrency(Currency.USD);
        walletRepository.save(wallet);
        return wallet;
    }
}
