package com.ertelecom.lesson_10;

public class AlreadyExistsException extends TaskServiceException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
