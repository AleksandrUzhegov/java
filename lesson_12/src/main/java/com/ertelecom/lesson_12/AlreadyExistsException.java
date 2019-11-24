package com.ertelecom.lesson_12;

public class AlreadyExistsException extends TaskServiceException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
