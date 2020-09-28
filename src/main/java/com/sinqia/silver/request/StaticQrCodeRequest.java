package com.sinqia.silver.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StaticQrCodeRequest {

    @NotEmpty
    @Schema(description = "Chave de endereçamento da conta transacional")
    private String key;

    @NotEmpty
    @Schema(description = "Nome do recebedor da ordem de pagamento instantâneo")
    private String merchantName;

    @NotEmpty
    @Schema(description = "Cidade de realização da ordem de pagamento instantâneoo")
    private String city;

    private BigDecimal financialValue;

    private String transactionIdentifier;

    private String adicionalInformation;
    
}
