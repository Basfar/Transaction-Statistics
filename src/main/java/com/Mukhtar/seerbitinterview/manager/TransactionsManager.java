package com.Mukhtar.seerbitinterview.manager;


import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.Mukhtar.seerbitinterview.exception.TransactionTimeInFutureException;
import com.Mukhtar.seerbitinterview.model.Transaction;
import com.Mukhtar.seerbitinterview.store.StatisticsStoreImpl;

import java.util.List;

public interface TransactionsManager {
    void addTransaction(Transaction transaction, long currentTimestamp) throws TransactionTimeInFutureException, TransactionOutOfRangeException;
    List<StatisticsStoreImpl> getValidStatisticsStore(long timestamp);
    void clear();
}
