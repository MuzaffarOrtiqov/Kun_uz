package com.example.Kun_uz.handler;

import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.exp.AppForbiddenException;
import com.example.Kun_uz.exp.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerController {
    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<String> handler(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handler(ResourceNotFoundException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(AppForbiddenException.class)
    public ResponseEntity<String> handler(AppForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handler(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }



}
