package com.sinqia.silver.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinqia.silver.entity.AdditionalInformation;
import com.sinqia.silver.entity.DynamicQrCode;
import com.sinqia.silver.exception.BusinessException;
import com.sinqia.silver.exception.UnableToGenerateQRCodeImageException;
import com.sinqia.silver.factory.DynamicQRCodeBuilderFactory;
import com.sinqia.silver.factory.StaticQRCodeBuilderFactory;
import com.sinqia.silver.repository.AdditionalInformationRepository;
import com.sinqia.silver.repository.DynamicQrCodeRepository;
import com.sinqia.silver.request.DynamicQrCodeRequest;
import com.sinqia.silver.request.DynamicQrCodeRequest.AdditionalInformationRequest;
import com.sinqia.silver.request.StaticQrCodeRequest;
import com.sinqia.silver.response.DynamicQrCodeResponse;
import com.sinqia.silver.response.StaticQrCodeResponse;

import net.glxn.qrgen.javase.QRCode;

@Service
public class QrCodeService extends ServiceBase {

    @Autowired
    private DynamicQrCodeRepository dynamicQrCodeRepository;

    @Autowired
    private AdditionalInformationRepository additionalInformationRepository;

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

    public DynamicQrCodeResponse generateDynamicQrCode(DynamicQrCodeRequest request) {

        String code = DynamicQRCodeBuilderFactory.getInstance().buildQRCodeString(request);
        DynamicQrCodeResponse response = new DynamicQrCodeResponse();

        if (request.getDeptor() != null) {
            if (request.getDeptor().getName() != null && request.getDeptor().getName().trim().length() > 0 &&
                    ((request.getDeptor().getCnpj() == null || request.getDeptor().getCnpj().trim().length() == 0) 
                    || (request.getDeptor().getCpf() == null || request.getDeptor().getCpf().trim().length() == 0))) {
                throw new BusinessException("Obrigatório informar o cpf ou cnpj ao informar o nome do devedor");
            }
        }

        try {
            DynamicQrCode dynamic = new DynamicQrCode();
            dynamic.setKey(request.getKey());
            dynamic.setCity(request.getCity());
            dynamic.setTransactionIdentifier(request.getTransactionIdentifier());
            if (request.getCalendar() != null) {
                dynamic.setExpiracyQuantity(request.getCalendar().getExpiracy());
                // dynamic.setDueDate(request.getCalendar().getDue());
                dynamic.setReceivableAfterMaturity(request.getCalendar().isReceivableAfterMaturity());
            }
            if (request.getDeptor() != null) {
                dynamic.setCpf(request.getDeptor().getCpf());
                dynamic.setCnpj(request.getDeptor().getCnpj());
                dynamic.setName(request.getDeptor().getName());
            }
            if (request.getValue() != null) {
                dynamic.setOriginal(request.getValue().getOriginal());
                dynamic.setFinale(request.getValue().getFinale());
                dynamic.setInterest(request.getValue().getInterest());
                dynamic.setPenalty(request.getValue().getPenalty());
                dynamic.setDiscount(request.getValue().getDiscount());
                dynamic.setAllowsChange(request.getValue().isAllowsChange());
            }
            dynamic.setPayerRequest(request.getPayerRequest());
            dynamicQrCodeRepository.save(dynamic);

            if (request.getAdditionalInformations() != null && request.getAdditionalInformations().size() > 0) {
                for (AdditionalInformationRequest itemInfo : request.getAdditionalInformations()) {
                    AdditionalInformation newInfo = new AdditionalInformation();
                    newInfo.setName(itemInfo.getName());
                    newInfo.setValue(itemInfo.getValue());
                    newInfo.setDynamicQrCode(dynamic);
                    additionalInformationRepository.save(newInfo);
                }

            }
        } catch (Exception e) {
            throw new BusinessException("Erro ao gravar dados da requisição", e);
        }

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
