package com.sinqia.silver.exception;

import java.util.List;

import com.sinqia.silver.response.SinqiaError;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -7261822821103494664L;

    private String exceptionLogCode;
    private String userMessage;
    private Object data;
    private List<SinqiaError> errors;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.data = data;
    }
    
    public BusinessException(String message, List<SinqiaError> errors) {
        super(message);
        this.errors = errors;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    public BusinessException(String message, String userMessage) {
        this(message, userMessage, null);
    }

}
