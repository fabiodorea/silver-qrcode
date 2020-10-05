package com.sinqia.silver.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "pix_qr_dinamico")
public class DynamicQrCode implements Serializable {

    private static final long serialVersionUID = 6471172231353601799L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tex_chave", length = 77, nullable = false)
    private String key;
    
    @Column(name = "tex_cidade", length = 15, nullable = false)
    private String city;
    
    @Column(name = "id_transacao", length = 35, nullable = false)
    private String transactionIdentifier;
    
    @Column(name = "qtd_expiracao", nullable = true)
    private Integer expiracyQuantity;
    
    @Column(name = "dat_vencimento", nullable = true)
    private LocalDate dueDate;
    
    @Column(name = "flg_recebivel_apos_vencimento", nullable = true)
    private boolean receivableAfterMaturity;
    
    @Column(name = "dev_cpf", length = 11, nullable = true)
    private String cpf;

    @Column(name = "dev_cnpj", length = 14, nullable = true)
    private String cnpj;
    
    @Column(name = "dev_nome", length = 200, nullable = true)
    private String name;
    
    @Column(name = "vr_original", nullable = false)
    private BigDecimal original;
    
    @Column(name = "vr_final", nullable = false)
    private BigDecimal finale;
    
    @Column(name = "vr_juros", nullable = true)
    private BigDecimal interest;
    
    @Column(name = "vr_multa", nullable = true)
    private BigDecimal penalty;
    
    @Column(name = "vr_desconto", nullable = true)
    private BigDecimal discount;
    
    @Column(name = "flg_permite_alteracao", nullable = true)
    private boolean allowsChange;
    
    @Column(name = "tex_solicitacao_pagador", length = 140, nullable = true)
    private String payerRequest;
    
    @OneToMany(mappedBy = "dynamicQrCode", fetch = FetchType.LAZY)
    private List<AdditionalInformation> additionalInformation;
}
