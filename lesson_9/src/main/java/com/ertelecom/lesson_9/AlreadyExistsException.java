package com.ertelecom.lesson_9;

public class AlreadyExistsException extends TaskServiceException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
