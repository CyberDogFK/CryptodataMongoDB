package com.antonp.cryptodatamongodb.lib;

public class HttpClientRequestException extends RuntimeException {
    public HttpClientRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
