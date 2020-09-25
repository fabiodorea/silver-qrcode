package com.sinqia.silver.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StaticQrCodeRequest {

    private String key;
    private String merchantName;
    private String city;
    private BigDecimal financialValue;
    private String transactionIdentifier;
    private String adicionalInformation;
    
}
