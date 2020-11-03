package com.stanula.usermanagement.exceptions.handler;

import com.stanula.usermanagement.service.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String UNEXPECTED_EXCEPTION_MESSAGE = "An unexpected server-side error occurred";

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity handlerEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handlerException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(new Date(), UNEXPECTED_EXCEPTION_MESSAGE,
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(errors.size() + 1 + ": " + error.getDefaultMessage()));
        ExceptionResponse response = new ExceptionResponse(new Date(), "Validation Failed", errors.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
