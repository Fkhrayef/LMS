package com.fkhrayef.lms.exceptions;

public class DuplicateBookException extends RuntimeException{
    private static final long serialVersionUID = 1;
    public DuplicateBookException(String message){
        super(message);
    }
}
