package com.sinqia.silver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinqia.silver.entity.DynamicQrCode;

import java.util.UUID;

public interface DynamicQrCodeRepository  extends JpaRepository<DynamicQrCode, UUID> {

    
}
