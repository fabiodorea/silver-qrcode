package com.sinqia.silver.response;

import lombok.Data;

@Data
public class DefaultResponse {

    private String code;
    private String message;

    public DefaultResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
