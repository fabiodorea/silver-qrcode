package com.sinqia.silver.gateway;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusResource {

    @GetMapping(path = "/")
    @PreAuthorize("hasAnyRole('user')")
    public String index() {
        return "external";
    }
}
