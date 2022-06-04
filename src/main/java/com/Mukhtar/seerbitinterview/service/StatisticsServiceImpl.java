package com.Mukhtar.seerbitinterview.service;

import com.Mukhtar.seerbitinterview.manager.TransactionsManager;
import com.Mukhtar.seerbitinterview.model.Statistics;
import com.Mukhtar.seerbitinterview.service.interfaces.StatisticsService;
import com.Mukhtar.seerbitinterview.store.StatisticsStoreImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private TransactionsManager transactionsManager;

    @Override
    public Statistics getStatistics() {
        long currentTime = System.currentTimeMillis();
        List<StatisticsStoreImpl> validStatisticsStore = transactionsManager.getValidStatisticsStore(currentTime);
        Statistics result = new Statistics();
        if (validStatisticsStore.isEmpty()) {
            result.resetToZero();
            return result;
        }
        validStatisticsStore.forEach(store -> store.addToResult(result));
        return result;
    }
}
