package com.bank.security.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

	@ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<APIException> handleRequestException(RequestException e) {
        APIException exception = new APIException(e.getMessage(),
                e.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(exception, e.getHttpStatus());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<APIException> handleEntityNotFoundException(EntityNotFoundException e) {
        APIException exception = new APIException(e.getMessage(),
                HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<APIException> handleNumberFormatException(NumberFormatException e) {
        APIException exception = new APIException(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        APIException exception = new APIException("The input provided is invalid",
                HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
