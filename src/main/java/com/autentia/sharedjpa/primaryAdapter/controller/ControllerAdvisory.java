package com.autentia.sharedjpa.primaryAdapter.controller;

import com.autentia.sharedjpa.core.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisory extends ResponseEntityExceptionHandler {

    @ExceptionHandler({FriendNotFoundException.class, NoBalanceFoundException.class,
            EmptyExpenseListException.class, EmptyFriendListException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(FriendNotFoundException ex, WebRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("message","Ups, algo no ha salido bien");
        map.put("Exception Message",ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NegativeExpenseAmountException.class)
    public ResponseEntity<Object> handleException(NegativeExpenseAmountException ex, WebRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("message","Ups, algo no ha salido bien");
        map.put("Exception Message",ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
