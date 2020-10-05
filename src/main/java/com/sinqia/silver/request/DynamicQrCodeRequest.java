package com.sinqia.silver.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DynamicQrCodeRequest implements Serializable {

    private static final long serialVersionUID = 2045868037506177952L;

    @Schema(description = "Chave de endereçamento da conta transacional")
    @NotEmpty(message = "Campo 'key' é obritagório")
    @Valid
    private String key;

    @NotEmpty(message = "Campo 'city' é obritagório")
    @Schema(description = "Cidade de realização da ordem de pagamento instantâneo")
    private String city;
    
    @NotEmpty(message = "Campo 'transactionIdentifier' é obritagório")
    @Schema(description = "identificador da transação utilizado para conciliação do recebedor")
    private String transactionIdentifier;
    
    private Calendar calendar;
    
    private Deptor deptor;
    
    private Value value;
    
    private String payerRequest;
    
    private List<AdditionalInformationRequest> additionalInformations;

    @Data
    public static class Calendar {
        private Integer expiracy;
        private String due;
        private boolean receivableAfterMaturity;
    }
    
    @Data
    public static class Deptor {
        private String cpf;
        private String cnpj;
        private String name;
    }
    
    @Data
    public static class Value {
        private BigDecimal original;
        private BigDecimal finale;
        private BigDecimal interest;
        private BigDecimal penalty;
        private BigDecimal discount;
        private boolean allowsChange;
    }
    
    @Data
    public static class AdditionalInformationRequest {
        private String name;
        private String value;
    }
    
}
