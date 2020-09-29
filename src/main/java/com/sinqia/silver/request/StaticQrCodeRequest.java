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
    @Schema(description = "Cidade de realização da ordem de pagamento instantâneo")
    private String city;

    @Schema(description = "valor financeiro da ordem de pagamento instantâneo")
    private BigDecimal financialValue;

    @Schema(description = "identificador da transação utilizado para conciliação do recebedor")
    private String transactionIdentifier;

    @Schema(description = "informaçãoes adicionais da ordem de pagamento instantâneo")
    private String adicionalInformation;
    
}
