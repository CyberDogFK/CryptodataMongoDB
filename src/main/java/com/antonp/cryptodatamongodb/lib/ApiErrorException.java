package com.antonp.cryptodatamongodb.lib;

public class ApiErrorException extends RuntimeException {
    public ApiErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
