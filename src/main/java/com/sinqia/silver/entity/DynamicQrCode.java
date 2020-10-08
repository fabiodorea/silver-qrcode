package com.sinqia.silver.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "pix_qr_dinamico")
public class DynamicQrCode implements Serializable {

    private static final long serialVersionUID = 6471172231353601799L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;
    
    @Column(name = "tex_chave", length = 77, nullable = false)
    private String key;
    
    @Column(name = "tex_cidade", length = 15, nullable = false)
    private String city;
    
    @Column(name = "id_transacao", length = 36, nullable = false)
    private String transactionIdentifier;
    
    @Column(name = "qtd_expiracao")
    private Integer expiracyQuantity;
    
    @Column(name = "dat_vencimento")
    private LocalDate dueDate;
    
    @Column(name = "flg_recebivel_apos_vencimento")
    private boolean receivableAfterMaturity;
    
    @Column(name = "dev_cpf", length = 11)
    private String cpf;

    @Column(name = "dev_cnpj", length = 14)
    private String cnpj;
    
    @Column(name = "dev_nome", length = 200)
    private String name;

    @Column(name = "rec_nome", length = 200)
    private String receiverName;
    
    @Column(name = "vr_original", nullable = false)
    private BigDecimal originalValue;
    
    @Column(name = "vr_final", nullable = false)
    private BigDecimal finalValue;
    
    @Column(name = "vr_juros")
    private BigDecimal interestValue;
    
    @Column(name = "vr_multa")
    private BigDecimal penaltyValue;
    
    @Column(name = "vr_desconto")
    private BigDecimal discountValue;

    @Column(name = "flg_permite_alteracao", nullable = true)
    private boolean allowsChange;
    
    @Column(name = "tex_solicitacao_pagador", length = 140, nullable = true)
    private String payerRequest;
    
    @OneToMany(mappedBy = "dynamicQrCode", fetch = FetchType.LAZY)
    private List<AdditionalInformation> additionalInformation;
}
