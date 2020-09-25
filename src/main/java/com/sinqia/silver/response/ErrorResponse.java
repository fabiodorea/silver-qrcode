package com.sinqia.silver.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErrorResponse extends DefaultResponse {

    private List<SinqiaError> body;

    public ErrorResponse(@JsonProperty("status") Integer status,
            @JsonProperty("code") String code,
            @JsonProperty("message") String message,
            @JsonProperty("body") List<SinqiaError> body) {
        super(status, code, message);
        this.body = body;
    }
    
    public ErrorResponse(@JsonProperty("status") Integer status,
            @JsonProperty("code") String code,
            @JsonProperty("message") String message) {
        super(status, code, message);
    }

}
