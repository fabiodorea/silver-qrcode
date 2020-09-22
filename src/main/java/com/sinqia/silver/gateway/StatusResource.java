package com.sinqia.silver.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusResource {

    @GetMapping(path = "/")
    public String index() {
        return "external";
    }
}
