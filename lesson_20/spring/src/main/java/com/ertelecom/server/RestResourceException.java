package com.ertelecom.server;

public class RestResourceException extends RuntimeException {
    public RestResourceException(String message) {
        super(message);
    }
}
