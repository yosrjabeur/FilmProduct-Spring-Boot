package com.example.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ActeurExceptionController {
    @ExceptionHandler(value = ActeurNotfoundException.class)
    public ResponseEntity<Object> exception(ActeurNotfoundException exception){
        return new ResponseEntity<>("Acteur not found", HttpStatus.NOT_FOUND);
    }
}
