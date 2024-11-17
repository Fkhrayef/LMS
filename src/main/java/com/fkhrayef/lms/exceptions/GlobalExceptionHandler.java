package com.fkhrayef.lms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleAllExceptions(Exception ex) {
        ErrorObject errorObject = buildErrorObject(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorObject> handleBookNotFoundException(BookNotFoundException ex) {
        ErrorObject errorObject = buildErrorObject(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ErrorObject> handleDuplicateBookException(DuplicateBookException ex) {
        ErrorObject errorObject = buildErrorObject(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    // Utility method to build error object ( avoiding DRY(Don't Repeat Yourself) )
    private ErrorObject buildErrorObject(HttpStatus status, String message) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(status.value());
        errorObject.setMessage(message);
        errorObject.setTimestamp(new Date());
        return errorObject;
    }
}
