package com.Mukhtar.seerbitinterview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Transaction time is out of range")
public class TransactionOutOfRangeException extends Exception{
}
