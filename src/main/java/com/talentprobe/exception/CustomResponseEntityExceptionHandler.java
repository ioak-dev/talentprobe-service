package com.talentprobe.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex,
      WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    ex.printStackTrace();
    if (exceptionResponse.getMessage().contains(HttpStatus.UNAUTHORIZED.toString())) {
      return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
    if (exceptionResponse.getMessage().equals(HttpStatus.CONFLICT.toString())) {
      return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(ResponseStatusException ex,
      WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
        ex.getStatusCode().toString(),
        request.getDescription(false));
    if (ex.getReason() != null) {
      exceptionResponse.setMessage(ex.getReason());
    }
    ex.printStackTrace();
    if (ex.getStatusCode().toString().equals(HttpStatus.UNAUTHORIZED.toString())) {
      return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
    if (ex.getStatusCode().toString().equals(HttpStatus.CONFLICT.toString())) {
      return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
    if (ex.getStatusCode().toString().equals(HttpStatus.INTERNAL_SERVER_ERROR.toString())) {
      return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (ex.getStatusCode().toString().equals(HttpStatus.NOT_FOUND.toString())) {
      return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public final ResponseEntity<ExceptionResponse> handleHttpClientErrorException(
      HttpClientErrorException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    ex.printStackTrace();
    return new ResponseEntity<>(exceptionResponse, ex.getStatusCode());
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public final ResponseEntity<ExceptionResponse> handleHttpServerErrorException(
      HttpServerErrorException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    ex.printStackTrace();
    return new ResponseEntity<>(exceptionResponse, ex.getStatusCode());
  }

  @ExceptionHandler(RestClientException.class)
  public final ResponseEntity<ExceptionResponse> handleRestClientException(RestClientException ex,
      WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    ex.printStackTrace();
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public final ResponseEntity<ExceptionResponse> handleUnauthorizedException(
      UnauthorizedException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
  }
}
