package com.Mukhtar.seerbitinterview.service;

import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.Mukhtar.seerbitinterview.exception.TransactionTimeInFutureException;
import com.Mukhtar.seerbitinterview.manager.TransactionsManager;
import com.Mukhtar.seerbitinterview.model.Statistics;
import com.Mukhtar.seerbitinterview.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsServiceTest {
    @Autowired
    private StatisticsServiceImpl statisticsService;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionsManager transactionsManager;

    @Before
    public void before(){
        transactionsManager.clear();
    }

    @Test
    public void testEmptyStatistics(){
        Statistics stats = statisticsService.getStatistics();
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), stats.getSum());
        assertEquals(0L, stats.getCount());
    }

    @Test
    public void testNonEmptyStatistics(){
        long time = Instant.now().toEpochMilli();
        final BigDecimal[] sum = {BigDecimal.ZERO};
        IntStream.range(0, 100).forEach(i->{
            Transaction t = new Transaction(BigDecimal.valueOf(i),time - i * 100L);
            sum[0] = sum[0].add(BigDecimal.valueOf(i));
            try {
                transactionService.addTransaction(t);
            } catch (TransactionOutOfRangeException | TransactionTimeInFutureException ignored) {}
        });

        Statistics stats = statisticsService.getStatistics();

        assertEquals(sum[0].setScale(2, RoundingMode.HALF_UP), stats.getSum());
        assertEquals(100, stats.getCount());
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), stats.getMin());
        assertEquals(BigDecimal.valueOf(99).setScale(2, RoundingMode.HALF_UP), stats.getMax());
    }
}
