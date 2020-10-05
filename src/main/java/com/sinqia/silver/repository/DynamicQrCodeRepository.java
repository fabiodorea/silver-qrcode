package com.sinqia.silver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinqia.silver.entity.DynamicQrCode;

public interface DynamicQrCodeRepository  extends JpaRepository<DynamicQrCode, Long> {

    
}
