package com.ertelecom.lesson_8;

public class AlreadyExistsException extends TaskServiceException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
