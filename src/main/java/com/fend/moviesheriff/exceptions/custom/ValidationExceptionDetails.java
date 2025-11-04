package com.fend.moviesheriff.exceptions.custom;

import com.fend.moviesheriff.exceptions.details.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final String fields;
    private final String defaultFieldsMessage;
}
