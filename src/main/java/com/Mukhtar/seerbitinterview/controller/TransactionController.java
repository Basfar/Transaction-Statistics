package com.Mukhtar.seerbitinterview.controller;

import com.Mukhtar.seerbitinterview.model.Transaction;
import com.Mukhtar.seerbitinterview.model.request.TransactionRequest;

import com.Mukhtar.seerbitinterview.service.TransactionServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    @Autowired
    private TransactionServiceImpl transactionService;

    @PostMapping("/transactions")
    @SneakyThrows
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.from(transactionRequest);
        transactionService.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<?> deleteAll() {
        transactionService.deleteAllTransactions();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
