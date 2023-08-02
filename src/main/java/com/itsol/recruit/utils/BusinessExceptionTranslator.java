package com.itsol.recruit.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class BusinessExceptionTranslator {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorVM> processSeAPartnerError(BusinessException ex) {
        ErrorVM errorVM = new ErrorVM(ex.getErrorCode(), ex.getMessage());
        BodyBuilder builder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        return builder.body(errorVM);
    }

    /**
     * Trả về message chung là trường không hợp lệ
     *
     * @param field
     * @return
     */
    private String getMessage(String field) {
        return field + " không hợp lệ";
    }
}
