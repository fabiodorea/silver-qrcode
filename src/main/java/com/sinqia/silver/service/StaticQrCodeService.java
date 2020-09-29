package com.sinqia.silver.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.sinqia.silver.exception.BusinessException;
import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.SinqiaError;
import com.sinqia.silver.response.StaticQrCodeResponse;

import net.glxn.qrgen.javase.QRCode;
import org.springframework.util.StringUtils;

@Service
public class StaticQrCodeService extends ServiceBase {

    public static SinqiaError KEY_ERROR = new SinqiaError("QRST.001", "Campo key é obritagório", "key", "Informe o valor do campo key");
    public static SinqiaError MERCHANT_ERROR = new SinqiaError("QRST.002", "Campo merchant é obritagório", "merchant", "Informe o valor do campo merchart");
    public static SinqiaError CITY_ERROR = new SinqiaError("QRST.003", "Campo city é obritagório", "city", "Informe o valor do campo city");

    private static NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
    static {
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setMinimumIntegerDigits(1);
        format.setGroupingUsed(false);
    }

    public StaticQrCodeResponse generateStaticQrCode(StaticQrCodeRequest request) throws Exception {
        validateRequest(request);
        if (errors.size() > 0) {
            throw new BusinessException("Erro ao gerar qr code estatico", errors);
        }

        String financialValue = format.format(request.getFinancialValue());

        StaticQrCodeResponse response = new StaticQrCodeResponse();

        String payloadFormatIndicator = "000201";
        String pointOfInitiationMethod = "010211"; // “11” (QR reutilizável) ou “12” (QR utilizável apenas uma vez
        String merchantAccountInformation = "2644" +
                "0014br.gov.bcb.pix" +
                "01" + getSize(request.getKey()) + request.getKey();
        String merchantCategoryCode = "52040000";
        String transactionCurrency = "5303986";
        String transactionAmount = "54" + getSize(financialValue) + financialValue;
        String countryCode = "5802BR";
        String merchantName = "59" + getSize(request.getMerchantName()) + request.getMerchantName();
        String merchantCity = "60" + getSize(request.getCity()) + request.getCity();
        String additionalDataField = "62" + getSize(request.getAdicionalInformation()) + request.getAdicionalInformation();
        String crc16 = "6304DFE3";

        String code = payloadFormatIndicator +
                pointOfInitiationMethod +
                merchantAccountInformation +
                merchantCategoryCode +
                transactionCurrency +
                transactionAmount +
                countryCode +
                countryCode +
                merchantName +
                merchantCity +
                additionalDataField +
                crc16;
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
    }

    private String getSize(String value) {
        return String.format("%02d", value.length());
    }

    private void validateRequest(StaticQrCodeRequest request) {
        if (StringUtils.isEmpty(request.getKey())) {
            errors.add(KEY_ERROR);
        }

        if (StringUtils.isEmpty(request.getMerchantName())) {
            errors.add(MERCHANT_ERROR);
        }

        if (StringUtils.isEmpty(request.getCity())) {
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
