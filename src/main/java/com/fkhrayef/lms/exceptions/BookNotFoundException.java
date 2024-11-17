package com.fkhrayef.lms.exceptions;

public class BookNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1;

    public BookNotFoundException(String message){
        super(message);
    }
}
