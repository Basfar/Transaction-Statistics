package com.Mukhtar.seerbitinterview.handler;

import com.Mukhtar.seerbitinterview.exception.TransactionOutOfRangeException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public void httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletResponse response) {
        if (ex.getCause() instanceof InvalidFormatException)
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        else
            response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    @ResponseBody
    @ExceptionHandler({NumberFormatException.class, DateTimeParseException.class})
    public void formatException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @ResponseBody
    @ExceptionHandler({TransactionOutOfRangeException.class})
    public void outOfRangeException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
