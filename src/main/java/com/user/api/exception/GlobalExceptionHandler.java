package com.user.api.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CadUsuarioException.class)
    public ResponseEntity<String> userNotFound (CadUsuarioException cadUsuarioException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cadUsuarioException.getMessage());
    }

}
