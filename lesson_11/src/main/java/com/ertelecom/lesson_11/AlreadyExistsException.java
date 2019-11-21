package com.ertelecom.lesson_11;

public class AlreadyExistsException extends TaskServiceException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
