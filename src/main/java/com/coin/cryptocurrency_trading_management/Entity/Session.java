package com.coin.cryptocurrency_trading_management.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Session  extends BaseEntity {
    private long userId;
    private LocalDateTime loginTime;
    private LocalDateTime expireTime;
    private boolean active;
    public Session(long userId) {
        super();
        this.userId = userId;
        this.loginTime = LocalDateTime.now();
        this.expireTime = loginTime.plusHours(2);
        this.active = true;
    }
}
