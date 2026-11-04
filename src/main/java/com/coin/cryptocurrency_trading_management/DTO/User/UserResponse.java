package com.coin.cryptocurrency_trading_management.DTO.User;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private long id;
    private String username;
    private Role role;
}
