package com.sinqia.silver.response;

import lombok.Data;

@Data
public class DynamicQrCodeResponse {

    private String documentIdentifier;
    private String textualContent;
    private String generatedImage;

}
