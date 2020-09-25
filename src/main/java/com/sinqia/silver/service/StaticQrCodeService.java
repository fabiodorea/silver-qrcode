package com.sinqia.silver.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.sinqia.silver.response.StaticQrCodeResponse;

import net.glxn.qrgen.javase.QRCode;

@Service
public class StaticQrCodeService {

    public StaticQrCodeResponse generateStaticQrCode() throws Exception {
        StaticQrCodeResponse response = new StaticQrCodeResponse();
        
        String text = "";
        BufferedImage img = generateQRCodeImage(text);
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", bos);
        byte[] imageBytes = bos.toByteArray();
        Encoder encoder = Base64.getEncoder();
        byte[] imageString = encoder.encode(imageBytes);
        String base64Image = new String(imageString);
        System.out.println(base64Image);
        
        //File outputfile = new File("C:\\Users\\Caju Tech\\Desktop\\TEM\\image3.jpg");
        //ImageIO.write(img, "jpg", outputfile);
        
        return response;
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
