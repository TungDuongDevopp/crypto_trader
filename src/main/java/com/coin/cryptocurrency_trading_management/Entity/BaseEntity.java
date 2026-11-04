package com.coin.cryptocurrency_trading_management.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class BaseEntity implements Identifiable {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseEntity() {
        createdAt = LocalDateTime.now();
    }
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
