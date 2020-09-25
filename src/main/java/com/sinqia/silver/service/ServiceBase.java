package com.sinqia.silver.service;

import java.util.ArrayList;
import java.util.List;

import com.sinqia.silver.response.SinqiaError;

public class ServiceBase {

    protected List<SinqiaError> errors = new ArrayList<SinqiaError>();

    public List<SinqiaError> getErrors() {
        return errors;
    }

    public void setErrors(List<SinqiaError> errors) {
        this.errors = errors;
    }

}
