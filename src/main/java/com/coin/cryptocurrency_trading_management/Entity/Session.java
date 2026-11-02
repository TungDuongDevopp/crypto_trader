package com.coin.cryptocurrency_trading_management.Entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Identifiable {

    @JsonAlias({"sessionId", "session_id"})
    private long id;
    private long userId;
    private LocalDateTime loginTime;
    private LocalDateTime expireTime;
    private boolean active;

    public Session(long userId) {
        this.userId = userId;
        this.loginTime = LocalDateTime.now();
        this.expireTime = loginTime.plusHours(2);
        this.active = true;
    }
}
