package com.sinqia.silver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(path = "/qr-code/static")
    public ResponseEntity<DefaultResponse> staticQrCode(@RequestBody StaticQrCodeRequest request) {
        try {
            LOGGER.info("POST | /v1/sinqia/qr-code/static | Inicializado | staticQrCode |");
            StaticQrCodeResponse response = staticQrCodeService.generateStaticQrCode(request);
            LOGGER.info("POST | /v1/sinqia/qr-code/static | Finalizado | staticQrCode |");
            return ResponseEntity.ok(new SuccessResponse<StaticQrCodeResponse>("1", "Código gerado com sucesso", response));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("S401", e.getMessage(), e.getErrors()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("S402", e.getMessage()));
        }
    }

}
