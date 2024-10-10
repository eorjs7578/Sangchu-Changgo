package com.d108.project.interfaces.advice;

import com.d108.project.domain.global.EmptyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<EmptyDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new EmptyDto(), HttpStatus.OK);
    }
}

