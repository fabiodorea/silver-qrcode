package com.sinqia.silver.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.sinqia.silver.exception.BusinessException;
import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.SinqiaError;
import com.sinqia.silver.response.StaticQrCodeResponse;
import com.sinqia.silver.util.StringUtils;

import net.glxn.qrgen.javase.QRCode;

@Service
public class StaticQrCodeService extends ServiceBase {

    public static SinqiaError KEY_ERROR        = new SinqiaError("QRST.001", "Campo key é obritagório", "key", "Informe o valor do campo key");
    public static SinqiaError MERCHANT_ERROR   = new SinqiaError("QRST.002", "Campo merchant é obritagório", "merchant", "Informe o valor do campo merchart");
    public static SinqiaError CITY_ERROR       = new SinqiaError("QRST.003", "Campo city é obritagório", "city", "Informe o valor do campo city");
    
    public StaticQrCodeResponse generateStaticQrCode(StaticQrCodeRequest request) throws Exception {
        validateRequest(request);
        if(errors.size() > 0) {
            throw new BusinessException("Erro ao gerar qr code estatico", errors);
        }
        
        StaticQrCodeResponse response = new StaticQrCodeResponse();
        
        String text = "fazer a concatenação do qr";
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
    
    private void validateRequest(StaticQrCodeRequest request) {
        if(StringUtils.isEmpty(request.getKey())) {
            errors.add(KEY_ERROR);
        }
        
        if(StringUtils.isEmpty(request.getMerchantName())) {
            errors.add(MERCHANT_ERROR);
        }
        
        if(StringUtils.isEmpty(request.getCity())) {
            errors.add(CITY_ERROR);
        }
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
