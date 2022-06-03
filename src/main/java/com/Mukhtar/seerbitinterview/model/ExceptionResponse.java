package com.Mukhtar.seerbitinterview.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private HttpStatus status;
    private String errorCode;
    private Instant timestamp = Instant.now();
    private String message;
    private String details;
    private String type;

    public ExceptionResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public ExceptionResponse(String message, String details, String type) {
        this.message = message;
        this.details = details;
        this.type = type;
    }

    public ExceptionResponse(HttpStatus status, String errorCode, String message, String details) {
        super();
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

}
