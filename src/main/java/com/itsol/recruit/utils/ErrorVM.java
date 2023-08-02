package com.itsol.recruit.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * View Model for transferring error message with a list of field errors.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final String message;
    private List<FieldErrorVM> fieldErrors;

    public ErrorVM(String code, String message, List<FieldErrorVM> fieldErrors) {
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public ErrorVM(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorVM(String code) {
        this(code, null);
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorVM(objectName, field, message));
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorVM> getFieldErrors() {
        return fieldErrors;
    }
}
