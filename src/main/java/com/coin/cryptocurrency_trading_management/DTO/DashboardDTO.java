package com.coin.cryptocurrency_trading_management.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {
    private String username;
    private long numberOfUser;
    private long numberOfSession;
    private long numberOfWallet;
    private long numberOfCoin;
}
