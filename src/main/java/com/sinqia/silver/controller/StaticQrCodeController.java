package com.sinqia.silver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.DefaultResponse;
import com.sinqia.silver.response.ErrorResponse;
import com.sinqia.silver.response.StaticQrCodeResponse;
import com.sinqia.silver.response.SuccessResponse;
import com.sinqia.silver.service.StaticQrCodeService;

@RestController
@RequestMapping("v1/sinqia")
public class StaticQrCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticQrCodeController.class);

    @Autowired
    private StaticQrCodeService staticQrCodeService;

    @GetMapping(path = "/qr-code/static2")
    public DefaultResponse staticQrCodeTest(int i) {
        if (i > 5) {
            StaticQrCodeResponse res = new StaticQrCodeResponse();
            res.setGeneratedImage("image er");
            res.setTextualContent("textual er");
            return new SuccessResponse<StaticQrCodeResponse>(200, "code", "messagem", res);
        } else {
            StaticQrCodeResponse res = new StaticQrCodeResponse();
            res.setGeneratedImage("image ss");
            res.setTextualContent("textual ss");
            return new ErrorResponse<StaticQrCodeResponse>(400, "1", "erro", "field", "solution", res);
        }
    }

    @PostMapping(path = "/qr-code/static")
    public DefaultResponse staticQrCode(@RequestBody StaticQrCodeRequest request) {
        try {
            LOGGER.info("POST | v1/sinqia/qr-code/static | Inicializado | staticQrCode |");
            StaticQrCodeResponse response = staticQrCodeService.generateStaticQrCode();
            LOGGER.info("POST | v1/sinqia/qr-code/static | Finalizado | staticQrCode |");
            return new SuccessResponse<StaticQrCodeResponse>(200, "code", "messagem", response);
        } catch (Exception e) {
            return new ErrorResponse<StaticQrCodeResponse>(400, "1", "erro", "field", "solution");
        }
    }

}
