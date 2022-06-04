package com.Mukhtar.seerbitinterview.service;


import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.Mukhtar.seerbitinterview.exception.TransactionTimeInFutureException;
import com.Mukhtar.seerbitinterview.manager.TransactionsManager;
import com.Mukhtar.seerbitinterview.model.Transaction;
import com.Mukhtar.seerbitinterview.service.interfaces.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionsManager transactionsStore;

    @Override
    public void addTransaction(Transaction transaction) throws TransactionOutOfRangeException, TransactionTimeInFutureException {
        long currentTimestamp = Instant.now().toEpochMilli();
        transactionsStore.addTransaction(transaction, currentTimestamp);
    }

    @Override
    public void deleteAllTransactions() {
        transactionsStore.clear();
    }
}
