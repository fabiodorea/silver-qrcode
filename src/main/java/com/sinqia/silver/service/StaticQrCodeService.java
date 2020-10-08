package com.sinqia.silver.service;

import com.sinqia.silver.exception.UnableToGenerateQRCodeImageException;
import com.sinqia.silver.factory.StaticQRCodeBuilderFactory;
import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.StaticQrCodeResponse;
import com.sinqia.silver.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StaticQrCodeService extends ServiceBase {

    public StaticQrCodeResponse generate(StaticQrCodeRequest request) {

        String code = StaticQRCodeBuilderFactory.getInstance().buildQRCodeString(request);
        StaticQrCodeResponse response = new StaticQrCodeResponse();

        try {
            String base64Image = ImageUtil.generateQRCodeImage(code);
            response.setGeneratedImage(base64Image);
            response.setTextualContent(code);
            return  response;
        } catch (IOException e) {
            throw new UnableToGenerateQRCodeImageException();
        }
    }
}
