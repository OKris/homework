package com.example.homework_Kristina.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrity(DataIntegrityViolationException e) {
        ErrorMessage message = new ErrorMessage();
        message.setMessage("Phone number already exists");
        message.setStatus(HttpStatus.BAD_REQUEST.value());
        message.setTimestamp(new Date());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage message = new ErrorMessage();
        message.setMessage(e.getMessage());
        message.setStatus(HttpStatus.BAD_REQUEST.value());
        message.setTimestamp(new Date());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
