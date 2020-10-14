package com.sinqia.silver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "spi_situacao_cobranca")
public class Situation implements Serializable {
    private static final long serialVersionUID = -665981554740189649L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sit_cob")
    private Long id;
    
    @Column(name = "dsc_sit_cob", length = 70, nullable = false)
    private String description;

}
