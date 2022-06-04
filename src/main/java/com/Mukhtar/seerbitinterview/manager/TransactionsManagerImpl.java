package com.Mukhtar.seerbitinterview.manager;


import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.Mukhtar.seerbitinterview.exception.TransactionTimeInFutureException;
import com.Mukhtar.seerbitinterview.model.Constant;
import com.Mukhtar.seerbitinterview.model.Transaction;
import com.Mukhtar.seerbitinterview.store.StatisticsStoreImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class TransactionsManagerImpl implements TransactionsManager {
    private StatisticsStoreImpl[] statisticsStores;

    @PostConstruct
    public void init() {
        statisticsStores = new StatisticsStoreImpl[Constant.TOTAL_WINDOW_SIZE_MILLIS / Constant.WINDOW_SIZE_MILLIS];
        initStores();
    }

    @Override
    public void addTransaction(Transaction transaction, long currentTimestamp) throws TransactionTimeInFutureException, TransactionOutOfRangeException {
        if (transaction.isValid(currentTimestamp)) {
            aggregate(transaction, currentTimestamp);
        }
    }


    @Override
    public List<StatisticsStoreImpl> getValidStatisticsStore(long currentTimestamp) {
        return Arrays.stream(statisticsStores)
                .filter(ss -> ss.isValid(currentTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        initStores();
    }

    private void initStores() {
        for (int i = 0; i< statisticsStores.length; i++) {
            statisticsStores[i] = new StatisticsStoreImpl();
        }
    }

    private void aggregate(Transaction transaction, long currentTimestamp) {
        int index = transaction.getIndex(currentTimestamp);
        StatisticsStoreImpl statisticsStore = statisticsStores[index];
        try {
            statisticsStore.getLock().writeLock().lock();
            if (statisticsStore.isEmpty()) {
                statisticsStore.create(transaction);
            } else {
                if (statisticsStore.isValid(currentTimestamp)) {
                    statisticsStore.merge(transaction);
                } else {
                    statisticsStore.clear();
                    statisticsStore.create(transaction);
                }
            }
        } finally {
            statisticsStore.getLock().writeLock().unlock();
        }
    }
}
