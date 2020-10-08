package com.sinqia.silver.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pix_informacao_adicional")
public class AdditionalInformation implements Serializable {
    private static final long serialVersionUID = -665981554740189649L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;
    
    @Column(name = "tex_nome", length = 50, nullable = false)
    private String name;
    
    @Column(name = "tex_valor", length = 200, nullable = false)
    private String value;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_qr_dinamico", referencedColumnName = "id", insertable = false, updatable = false)
    private DynamicQrCode dynamicQrCode;

}
