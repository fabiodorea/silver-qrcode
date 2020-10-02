package com.sinqia.silver.request;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaticQrCodeRequest implements Serializable {

    @Schema(description = "Chave de endereçamento da conta transacional")
    @NotEmpty(message = "Campo 'key' é obritagório")
    @Valid
    private String key;

    @NotEmpty(message = "Campo 'merchantName' é obritagório")
    @Schema(description = "Nome do recebedor da ordem de pagamento instantâneo")
    private String merchantName;

    @NotEmpty(message = "Campo 'city' é obritagório")
    @Schema(description = "Cidade de realização da ordem de pagamento instantâneo")
    private String city;

    @Schema(description = "valor financeiro da ordem de pagamento instantâneo")
    private BigDecimal financialValue;

    @Schema(description = "identificador da transação utilizado para conciliação do recebedor")
    private String transactionIdentifier;

    @Schema(description = "informaçãoes adicionais da ordem de pagamento instantâneo")
    private String adicionalInformation;
    
}
