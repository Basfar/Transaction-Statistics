package com.Mukhtar.seerbitinterview.model;


import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.Mukhtar.seerbitinterview.exception.TransactionTimeInFutureException;
import com.Mukhtar.seerbitinterview.model.request.TransactionRequest;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Slf4j
public class Transaction {
    @NonNull
    private BigDecimal amount;
    @NonNull
    private long timestamp;

    public static Transaction from(TransactionRequest transactionRequest) {
        Instant instant = Instant.parse(transactionRequest.getTimestamp());
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(transactionRequest.getAmount()));
        long timestamp = instant.toEpochMilli();
        return new Transaction(amount, timestamp);
    }



    public boolean isValid(long currentTimestamp) throws TransactionTimeInFutureException, TransactionOutOfRangeException {
        if (currentTimestamp < getTimestamp()) {
            throw new TransactionTimeInFutureException();
        }
        if (currentTimestamp - getTimestamp() >= Constant.TOTAL_WINDOW_SIZE_MILLIS) {
            throw new TransactionOutOfRangeException();
        }
        return true;
    }

    public int getIndex(long currentTimestamp) {
        long transactionTime = getTimestamp();
        return (int)((currentTimestamp - transactionTime) / Constant.WINDOW_SIZE_MILLIS) % (Constant.TOTAL_WINDOW_SIZE_MILLIS / Constant.WINDOW_SIZE_MILLIS);
    }
}
