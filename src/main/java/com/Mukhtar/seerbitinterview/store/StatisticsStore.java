package com.Mukhtar.seerbitinterview.store;


import com.Mukhtar.seerbitinterview.model.Statistics;
import com.Mukhtar.seerbitinterview.model.Transaction;

public interface StatisticsStore {
    void create(Transaction transaction);
    void addToResult(Statistics result);
    void merge(Transaction transaction);
    boolean isEmpty();
    void clear();

     // currentTimestamp check whether statistics store is valid for current time

    boolean isValid(long currentTimestamp);
}
