package com.sinqia.silver.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinqia.silver.enums.SuccessCode;
import com.sinqia.silver.request.DynamicQrCodeRequest;
import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.DefaultResponse;
import com.sinqia.silver.response.DynamicQrCodeResponse;
import com.sinqia.silver.response.ErrorResponse;
import com.sinqia.silver.response.StaticQrCodeResponse;
import com.sinqia.silver.response.SuccessResponse;
import com.sinqia.silver.service.QrCodeService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/v1")
@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
public class QrCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeController.class);

    @Autowired
    private QrCodeService staticQrCodeService;

    @PostMapping(path = "/qr-code/static", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DefaultResponse> staticQrCode(@Valid @RequestBody StaticQrCodeRequest request) {
        
        StaticQrCodeResponse response = staticQrCodeService.generateStaticQrCode(request);
        return ResponseEntity.ok(SuccessResponse.builder()
                .code(SuccessCode.QR_CODE_GENERATED.getCode())
                .message(SuccessCode.QR_CODE_GENERATED.getMessage())
                .body(response)
                .build());
    }
    
    @PostMapping(path = "/qr-code/dynamic", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DefaultResponse> dynamicQrCode(@Valid @RequestBody DynamicQrCodeRequest request) {
        DynamicQrCodeResponse response = staticQrCodeService.generateDynamicQrCode(request);
        return ResponseEntity.ok(SuccessResponse.builder()
                .code(SuccessCode.QR_CODE_GENERATED.getCode())
                .message(SuccessCode.QR_CODE_GENERATED.getMessage())
                .body(response)
                .build());
    }

}
