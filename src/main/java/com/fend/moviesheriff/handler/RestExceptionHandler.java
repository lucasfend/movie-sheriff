package com.fend.moviesheriff.handler;

import com.fend.moviesheriff.exceptions.custom.NotFoundExceptionDetails;
import com.fend.moviesheriff.exceptions.custom.SQLConstraintViolationExceptionDetails;
import com.fend.moviesheriff.exceptions.custom.ValidationExceptionDetails;
import com.fend.moviesheriff.exceptions.httpstatus.BadRequestException;
import com.fend.moviesheriff.exceptions.httpstatus.SQLConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<NotFoundExceptionDetails> handleNotFoundExceptionDetails(BadRequestException e) {
        return new ResponseEntity<>(
                NotFoundExceptionDetails.builder()
                        .title("Bad Request Exception - Entity not found, contact the developer by email")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .developerMessage(e.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> logOfFieldsWithError = e.getBindingResult().getFieldErrors();
        String fieldWithError = logOfFieldsWithError.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String defaultFieldsMessage = logOfFieldsWithError.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .title("Bad Request Exception - Invalid fields, please fill them correctly")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .developerMessage(e.getClass().getName())
                        .fields(fieldWithError)
                        .defaultFieldsMessage(defaultFieldsMessage)
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SQLConstraintViolationException.class)
    public ResponseEntity<SQLConstraintViolationExceptionDetails> handlePSQLException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(
                SQLConstraintViolationExceptionDetails.builder()
                        .title("Data Integrity Violation Exception - Rating already registered by user")
                        .status(HttpStatus.CONFLICT.value())
                        .message(e.getMostSpecificCause().getMessage())
                        .developerMessage(e.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.CONFLICT
        );
    }

}
