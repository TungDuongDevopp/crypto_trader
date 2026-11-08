package com.coin.cryptocurrency_trading_management.Util.Mapper;

import com.coin.cryptocurrency_trading_management.DTO.Wallet.AdminWalletResponse;
import com.coin.cryptocurrency_trading_management.DTO.Wallet.UserWalletResponse;
import com.coin.cryptocurrency_trading_management.Entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "totalDeposited", ignore = true)
    @Mapping(target = "totalWithdrawn", ignore = true)
    UserWalletResponse toUserWalletResponse(Wallet wallet);

    AdminWalletResponse toAdminWalletResponse(Wallet wallet);
    List<AdminWalletResponse> toAdminWalletResponseList(List<Wallet> wallets);
}
