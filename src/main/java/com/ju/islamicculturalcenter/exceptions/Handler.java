package com.ju.islamicculturalcenter.exceptions;

import com.ju.islamicculturalcenter.dto.response.CODE;
import com.ju.islamicculturalcenter.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
public class Handler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<?>> notFoundException(NotFoundException exception){
        log.error("Error during request processing.", exception);
        Response<Object> response = Response.builder()
                .message(exception.getMessage())
                .code(CODE.NOT_FOUND.getId())
                .success(false)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CustomBadCredentialsException.class)
    public ResponseEntity<Response<?>> exception(CustomBadCredentialsException exception) {
        log.error("Error during request processing.", exception);
        Response<Object> response = Response.builder()
                .message(exception.getMessage())
                .code(CODE.UNAUTHORIZED.getId())
                .success(false)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Response<?>> exception(UserNotFoundException exception) {
        log.error("Error during request processing.", exception);
        Response<Object> response = Response.builder()
                .message(exception.getMessage())
                .code(CODE.UNAUTHORIZED.getId())
                .success(false)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Response<?>> exception(ValidationException exception) {
        log.error("Error during request processing.", exception);
        Response<Object> response = Response.builder()
                .message(exception.getMessage())
                .code(CODE.BAD_REQUEST.getId())
                .success(false)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Response<?>> exception(SQLIntegrityConstraintViolationException exception) {
        log.error("Error during request processing.", exception);
        Response<Object> response = Response.builder()
                .message(exception.getMessage())
                .code(CODE.BAD_REQUEST.getId())
                .success(false)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
