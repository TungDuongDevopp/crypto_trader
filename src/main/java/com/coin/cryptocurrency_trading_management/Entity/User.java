package com.coin.cryptocurrency_trading_management.Entity;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Role;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class User  extends BaseEntity{
    private String username;
    @ToString.Exclude
    private String password;
    private Role role;

}

