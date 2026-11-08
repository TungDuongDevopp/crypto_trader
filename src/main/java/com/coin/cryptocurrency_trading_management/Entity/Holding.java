package com.coin.cryptocurrency_trading_management.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Holding extends BaseEntity {
    private long userId;
    private long coinId;
    private BigDecimal quantity;
    private BigDecimal averageBuyPrice;
    private BigDecimal price;

}
