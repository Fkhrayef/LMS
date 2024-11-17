package com.fkhrayef.lms.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<HashMap<String, List<String>>> handleBindException(BindException ex) {
        List<String> errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        HashMap<String, List<String>> errMap = new HashMap<>();
        errMap.put("errors", errors);
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }
}
