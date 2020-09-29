package com.sinqia.silver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinqia.silver.exception.BusinessException;
import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.DefaultResponse;
import com.sinqia.silver.response.ErrorResponse;
import com.sinqia.silver.response.StaticQrCodeResponse;
import com.sinqia.silver.response.SuccessResponse;
import com.sinqia.silver.service.StaticQrCodeService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/v1/sinqia")
@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
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
            return new ErrorResponse(400, "1", "erro");
        }
    }

    @PostMapping(path = "/qr-code/static")
    public DefaultResponse staticQrCode(@RequestBody StaticQrCodeRequest request) {
        try {
            LOGGER.info("POST | /v1/sinqia/qr-code/static | Inicializado | staticQrCode |");
            StaticQrCodeResponse response = staticQrCodeService.generateStaticQrCode(request);
            LOGGER.info("POST | /v1/sinqia/qr-code/static | Finalizado | staticQrCode |");
            return new SuccessResponse<StaticQrCodeResponse>(200, "1", "Código gerado com sucesso", response);
        } catch (BusinessException e) {
            return new ErrorResponse(400, "S401", e.getMessage(), e.getErrors());
        } catch (Exception e) {
            return new ErrorResponse(400, "S402", e.getMessage());
        }
    }

}
