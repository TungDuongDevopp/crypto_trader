package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.DTO.Wallet.AdminWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.UserWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.WalletChangeStatusRequest;
import com.coin.cryptocurrency_trading_management.Entity.Enum.Currency;
import com.coin.cryptocurrency_trading_management.Entity.Enum.WalletStatus;
import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import com.coin.cryptocurrency_trading_management.Repository.Interface.WalletRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import com.coin.cryptocurrency_trading_management.Util.Exception.CanMaltipulateDataException;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import com.coin.cryptocurrency_trading_management.Util.Mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private String generateWalletAddress() {
        byte[] bytes = new byte[16];
        SECURE_RANDOM.nextBytes(bytes);
        return "WLT-" + HexFormat.of().withUpperCase().formatHex(bytes);
    }

    private void checkAccess(Wallet wallet) {
        if (wallet.getStatus() == WalletStatus.CLOSED) {
            throw new CanMaltipulateDataException("Can Maltipulate Data");
        }
    }

    @Override
    public void createWallet(long userId) throws IOException {
        var wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAddress(generateWalletAddress());
        wallet.setBalance(new BigDecimal("300"));
        wallet.setCurrency(Currency.USD);
        walletRepository.save(wallet);
    }

    @Override
    public UserWalletResponse getWalletByUserId(long userId) throws IOException {
        var wallet = walletRepository.getWalletByUserId(userId);
        return walletMapper.toUserWalletResponse(wallet);
    }

    @Override
    public AdminWalletResponse changeStatus(WalletChangeStatusRequest walletChangeStatusRequest) throws IOException {
        var wallet = walletRepository.getById(walletChangeStatusRequest.getId());
        if (wallet == null) {
            throw new ResourceNotFoundException("Wallet Not Found");
        }
        checkAccess(wallet);
        wallet.setStatus(walletChangeStatusRequest.getStatus());
        walletRepository.updateById(walletChangeStatusRequest.getId(), wallet);
        return walletMapper.toAdminWalletResponse(wallet);
    }

    @Override
    public List<AdminWalletResponse> getAll() throws IOException {
        var wallets = walletRepository.getAll();
        return walletMapper.toAdminWalletResponseList(wallets);
    }

    @Override
    public void deductBalance(long userId, BigDecimal amount) throws IOException {
        Wallet wallet = walletRepository.getWalletByUserId(userId);
        checkAccess(wallet);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException(
                    "Insufficient balance. Available: " + wallet.getBalance() + ", Required: " + amount
            );
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.updateById(wallet.getId(), wallet);
    }

    @Override
    public void addBalance(long userId, BigDecimal amount) throws IOException {
        Wallet wallet = walletRepository.getWalletByUserId(userId);
        checkAccess(wallet);
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.updateById(wallet.getId(), wallet);
    }
}
