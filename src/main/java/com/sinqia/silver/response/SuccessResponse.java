package com.sinqia.silver.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SuccessResponse<T> extends DefaultResponse {

    private T body;

    public SuccessResponse(@JsonProperty("status") Integer status,
            @JsonProperty("code") String code,
            @JsonProperty("message") String message,
            @JsonProperty("body") T body) {
        super(status, code, message);
        this.body = body;
    }

}
