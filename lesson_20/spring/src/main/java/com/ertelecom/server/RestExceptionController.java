package com.ertelecom.server;

import com.ertelecom.gwt.common.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionController {
    @ExceptionHandler
    public ResponseEntity<?> exceptionInterceptor(RestResourceException exc) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
