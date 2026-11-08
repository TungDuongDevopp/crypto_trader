package com.coin.cryptocurrency_trading_management.Entity;

import com.coin.cryptocurrency_trading_management.Entity.Enum.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction extends BaseEntity {
    private long orderId;
    private BigDecimal quantity;
    private BigDecimal totalAmount;
    private BigDecimal executionPrice;
    private LocalDateTime transactionTime;
    private TransactionStatus status;
    public Transaction() {
        super();
        status = TransactionStatus.PENDING;
    }

}
