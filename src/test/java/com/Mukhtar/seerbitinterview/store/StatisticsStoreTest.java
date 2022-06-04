package com.Mukhtar.seerbitinterview.store;

import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.Mukhtar.seerbitinterview.exception.TransactionTimeInFutureException;
import com.Mukhtar.seerbitinterview.manager.TransactionsManager;
import com.Mukhtar.seerbitinterview.model.Statistics;
import com.Mukhtar.seerbitinterview.model.Transaction;
import com.Mukhtar.seerbitinterview.service.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsStoreTest {
    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionsManager transactionsManager;

    @Test
    public void testCreateFromTransaction() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(12.5), 123L);
        StatisticsStoreImpl statisticsStore = new StatisticsStoreImpl();
        statisticsStore.create(transaction);
        assertEquals(123L, statisticsStore.getTimestamp());
        assertEquals("12.50", statisticsStore.getStatistics().getSum().toString());
        assertEquals(1, statisticsStore.getStatistics().getCount());
    }

    @Test
    public void testAggregateResult() {
        long time = Instant.now().toEpochMilli();
        final BigDecimal[] sum = {BigDecimal.ZERO};
        IntStream.range(0, 100).forEach(i->{
            Transaction t = new Transaction(BigDecimal.valueOf(i),time - i * 100L);
            sum[0] = sum[0].add(BigDecimal.valueOf(i));
            try {
                transactionService.addTransaction(t);
            } catch (TransactionOutOfRangeException | TransactionTimeInFutureException ignored) {}
        });
        Statistics result = new Statistics();
        List<StatisticsStoreImpl> validStatisticsStore = transactionsManager.getValidStatisticsStore(System.currentTimeMillis());
        validStatisticsStore.forEach(store -> store.addToResult(result));

        Statistics expected = new Statistics();
        expected.setMin(BigDecimal.ZERO);
        expected.setMax(BigDecimal.valueOf(99));
        expected.setCount(100);
        expected.setSum(BigDecimal.valueOf(4950));
        expected.setAvg(BigDecimal.valueOf(49.5));
        assertEquals(expected, result);
    }
}
