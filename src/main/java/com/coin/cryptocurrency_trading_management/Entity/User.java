package com.coin.cryptocurrency_trading_management.Entity;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class User  implements Identifiable{
    private long id;
    private String username;
    @ToString.Exclude
    private String password;
}

