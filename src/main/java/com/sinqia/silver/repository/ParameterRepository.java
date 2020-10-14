package com.sinqia.silver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinqia.silver.entity.Parameter;

public interface ParameterRepository  extends JpaRepository<Parameter, Long> {

    
}
