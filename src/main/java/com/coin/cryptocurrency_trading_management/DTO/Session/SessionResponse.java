package com.coin.cryptocurrency_trading_management.DTO.Session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class SessionResponse {
    private long id;
    private long userId;
    private LocalDateTime loginTime;
    private LocalDateTime expireTime;
    private boolean active;
}
