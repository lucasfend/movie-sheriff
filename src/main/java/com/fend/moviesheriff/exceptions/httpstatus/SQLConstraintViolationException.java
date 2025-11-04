package com.fend.moviesheriff.exceptions.httpstatus;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SQLConstraintViolationException extends DataIntegrityViolationException {
    public SQLConstraintViolationException(String msg) {
        super(msg);
    }
}
