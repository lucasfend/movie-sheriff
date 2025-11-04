package com.fend.moviesheriff.exceptions.details;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected Integer status;
    protected String message;
    protected String developerMessage;
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    protected LocalDateTime timestamp;
}
