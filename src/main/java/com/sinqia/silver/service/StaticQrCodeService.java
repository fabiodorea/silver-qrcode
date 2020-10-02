package com.sinqia.silver.service;

import com.sinqia.silver.exception.UnableToGenerateQRCodeImageException;
import com.sinqia.silver.factory.StaticQRCodeBuilderFactory;
import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.StaticQrCodeResponse;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

@Service
public class StaticQrCodeService extends ServiceBase {


    public StaticQrCodeResponse generateStaticQrCode(StaticQrCodeRequest request) {

        String code = StaticQRCodeBuilderFactory.getInstance().buildQRCodeString(request);
        StaticQrCodeResponse response = new StaticQrCodeResponse();

        try {
            BufferedImage img = generateQRCodeImage(code);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
            Encoder encoder = Base64.getEncoder();
            byte[] imageString = encoder.encode(imageBytes);
            String base64Image = new String(imageString);

            response.setGeneratedImage(base64Image);
            response.setTextualContent(code);
            return response;
        } catch (IOException e) {
            throw new UnableToGenerateQRCodeImageException();
        }
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws IOException {
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());

        return ImageIO.read(bis);
    }

}
