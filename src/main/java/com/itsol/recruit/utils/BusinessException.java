package com.itsol.recruit.utils;



import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by ITSOL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        super(message);
        errorCode = "";
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.errorCode = "";
    }

    public String getErrorCode() {
        if (errorCode == null) {
            return "";
        }
        return errorCode.trim();
    }
}
