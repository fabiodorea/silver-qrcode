package com.sinqia.silver.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    @Size(max = 35, message =  "Campo 'transactionIdentifier' não pode exceder o tamanho máximo de 35 caracteres.")
    @Schema(description = "identificador da transação utilizado para conciliação do recebedor")
    private String transactionIdentifier;
    
    private Calendar calendar;
    
    private Debtor debtor;

    private Receiver receiver;

    private Value value;
    
    private String payerRequest;
    
    private List<AdditionalInformationRequest> additionalInformations;

    @Data
    public static class Calendar {
        private Integer expiracy;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate due;
        private boolean receivableAfterMaturity;
    }
    
    @Data
    public static class Debtor {
        private String cpf;
        private String cnpj;
        private String name;
    }

    @Data
    public static class Receiver {
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
