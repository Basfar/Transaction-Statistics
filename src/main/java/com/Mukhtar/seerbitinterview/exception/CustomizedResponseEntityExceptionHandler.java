package com.Mukhtar.seerbitinterview.exception;

import com.Mukhtar.seerbitinterview.model.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseStatusExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidArguments(MethodArgumentTypeMismatchException ex,
                                                                          WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        exceptionResponse.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setDetails(request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValueNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleValueNotFoundException(ValueNotFoundException ex,
                                                                                WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        exceptionResponse.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setDetails(request.getDescription(false));
        if(ex.getMessage().contains("future"))
            return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {


        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        exceptionResponse.setErrorCode(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY));
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setDetails(request.getDescription(false));
        if(!(ex.getMessage().contains("amount")|| ex.getMessage().contains("timestamp")))
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionResponse.setErrorCode("500");
        exceptionResponse.setMessage("Json output Error");
        exceptionResponse.setDetails(request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        exceptionResponse.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
        exceptionResponse.setMessage("Json MalFormed");

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            for (FieldError e : errors) {
                message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
            }

            exceptionResponse.setDetails(message.toString());
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
