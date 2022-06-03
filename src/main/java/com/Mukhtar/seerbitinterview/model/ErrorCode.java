package com.Mukhtar.seerbitinterview.model;

public enum ErrorCode {
    INVALID_REQUEST_JSON(400, "Bad Request - json error"), UNPARSEABLE_ERROR(422, "Unparsable Error - field error");

    private Integer errorCode;
    private String errorMessage;

    ErrorCode(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
