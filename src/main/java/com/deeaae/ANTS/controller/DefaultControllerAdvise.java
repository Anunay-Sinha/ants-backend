package com.deeaae.ANTS.controller;


import com.deeaae.ANTS.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultControllerAdvise extends ResponseEntityExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity handleAuthenticationFailureException(RuntimeException ex,
      WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse("001", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }



}
