package com.sinqia.silver.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class SuccessResponse extends DefaultResponse {

    private Object body;

}
