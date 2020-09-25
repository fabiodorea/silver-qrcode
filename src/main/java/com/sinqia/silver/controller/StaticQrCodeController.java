package com.sinqia.silver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinqia.silver.response.DefaultResponse;
import com.sinqia.silver.response.ErrorResponse;
import com.sinqia.silver.response.StaticQrCodeResponse;
import com.sinqia.silver.response.SuccessResponse;

import net.glxn.qrgen.javase.QRCode;

@RestController
@RequestMapping("api/sinqia")
public class StaticQrCodeController {

    @GetMapping(path = "/qr-code/static")
    public DefaultResponse staticQrCode(int i) {
        if (i > 5) {
            StaticQrCodeResponse res = new StaticQrCodeResponse();
            res.setGeneratedImage("image er");
            res.setTextualContent("textual er");
            return new SuccessResponse<StaticQrCodeResponse>(200, "code", "messagem", res );
        }else {
            StaticQrCodeResponse res = new StaticQrCodeResponse();
            res.setGeneratedImage("image ss");
            res.setTextualContent("textual ss");
            return new ErrorResponse<StaticQrCodeResponse>(400, "1", "erro", "field", "solution", res);
        }
    }

    public static void main(String[] args) throws Exception {
        File outputfile = new File("C:\\Users\\Caju Tech\\Desktop\\TEM\\image2.jpg");
        BufferedImage img = generateQRCodeImage("fabio paes");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        
        //ImageIO.write(img, "jpg", outputfile);
        ImageIO.write(img, "jpg", bos);
        byte[] imageBytes = bos.toByteArray();
        Base64Encoder encoderX = new Base64Encoder();
        encoderX.decode("dfdfd", bos2);
        System.out.println(bos2);
        Encoder encoder = Base64.getEncoder();
        byte[] imageString = encoder.encode(imageBytes);
        System.out.println("serializado: " + imageString);
        
    }
    
    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        ByteArrayOutputStream stream = QRCode
          .from(barcodeText)
          .withSize(250, 250)
          .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
     
        return ImageIO.read(bis);
    }
    
}
