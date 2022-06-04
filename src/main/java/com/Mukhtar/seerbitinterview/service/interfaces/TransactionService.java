package com.Mukhtar.seerbitinterview.service.interfaces;


import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.Mukhtar.seerbitinterview.exception.TransactionTimeInFutureException;
import com.Mukhtar.seerbitinterview.model.Transaction;

public interface TransactionService {
    void addTransaction(Transaction transaction) throws TransactionOutOfRangeException, TransactionTimeInFutureException;
    void deleteAllTransactions();
}
