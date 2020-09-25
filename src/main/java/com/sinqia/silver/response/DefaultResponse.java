package com.sinqia.silver.response;

import lombok.Data;

@Data
public class DefaultResponse {

    private Integer status;
    private String code;
    private String message;

    public DefaultResponse(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
