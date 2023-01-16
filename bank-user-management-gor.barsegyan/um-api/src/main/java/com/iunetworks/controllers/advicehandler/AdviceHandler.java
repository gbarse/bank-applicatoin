package com.iunetworks.controllers.advicehandler;

import com.iunetworks.exceptions.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceHandler {



  @ExceptionHandler({ApplicationException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(ApplicationException ex) {
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.status());
  }

//  @ExceptionHandler({RuntimeException.class})
//  public ResponseEntity<Object> handleRuntimeException(final RuntimeException ex) {
//    return new ResponseEntity<>("Something went wrong.", new HttpHeaders(), 500);
//  }


}

