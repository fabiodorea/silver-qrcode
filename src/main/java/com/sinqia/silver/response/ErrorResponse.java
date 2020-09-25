package com.sinqia.silver.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErrorResponse<T> extends DefaultResponse {

    private String field;
    private String solution;
    private T body;

    public ErrorResponse(@JsonProperty("status") Integer status,
            @JsonProperty("code") String code,
            @JsonProperty("message") String message,
            @JsonProperty("field") String field,
            @JsonProperty("solution") String solution,
            @JsonProperty("body") T body) {
        super(status, code, message);
        this.body = body;
    }
    
    public ErrorResponse(@JsonProperty("status") Integer status,
            @JsonProperty("code") String code,
            @JsonProperty("message") String message,
            @JsonProperty("field") String field,
            @JsonProperty("solution") String solution) {
        super(status, code, message);
    }

}
