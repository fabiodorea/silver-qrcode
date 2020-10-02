package com.sinqia.silver.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class DefaultResponse {

    private String code;
    private String message;

}
