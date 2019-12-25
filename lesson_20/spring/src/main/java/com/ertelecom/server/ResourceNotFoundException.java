package com.ertelecom.server;

public class ResourceNotFoundException extends RestResourceException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}