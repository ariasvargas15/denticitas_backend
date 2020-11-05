package com.amongusdev.exception;

import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GenericResponse {
    private String message;
    private int code;
    private String error;

    public GenericResponse(final String message, final int code) {
        super();
        this.message = message;
        this.code = code;
    }

    public GenericResponse(final String message, final String error, final int code) {
        super();
        this.message = message;
        this.error = error;
        this.code = code;
    }

    public GenericResponse(List<ObjectError> allErrors, String error, final int code) {
        this.error = error;
        String temp = allErrors.stream().map(e -> {
            if (e instanceof FieldError) {
                return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
            } else {
                return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
            }
        }).collect(Collectors.joining(","));
        this.message = "[" + temp + "]";
        this.code = code;
    }

}

