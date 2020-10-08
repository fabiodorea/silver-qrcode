package com.sinqia.silver.service;

import com.sinqia.silver.entity.AdditionalInformation;
import com.sinqia.silver.entity.DynamicQrCode;
import com.sinqia.silver.exception.BusinessException;
import com.sinqia.silver.exception.UnableToGenerateQRCodeImageException;
import com.sinqia.silver.factory.DynamicQRCodeBuilderFactory;
import com.sinqia.silver.repository.AdditionalInformationRepository;
import com.sinqia.silver.repository.DynamicQrCodeRepository;
import com.sinqia.silver.request.DynamicQrCodeRequest;
import com.sinqia.silver.response.DynamicQrCodeResponse;
import com.sinqia.silver.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class DynamicQrCodeService {

    @Autowired
    private DynamicQrCodeRepository dynamicQrCodeRepository;

    @Autowired
    private AdditionalInformationRepository additionalInformationRepository;

    @Autowired
    private DynamicQRCodeBuilderFactory dynamicQRCodeBuilderFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public DynamicQrCodeResponse generate(DynamicQrCodeRequest request) {

        DynamicQrCodeResponse response = new DynamicQrCodeResponse();
        String code;
        if (request.getDebtor() != null && !StringUtils.isEmpty(request.getDebtor().getName()) &&
                (StringUtils.isEmpty(request.getDebtor().getCnpj())
                        && StringUtils.isEmpty(request.getDebtor().getCpf()))) {
            throw new BusinessException("Obrigatório informar o cpf ou cnpj ao informar o nome do devedor");
        }

        DynamicQrCode dynamic = new DynamicQrCode();
        dynamic.setKey(request.getKey());
        dynamic.setCity(request.getCity());
        dynamic.setTransactionIdentifier(request.getTransactionIdentifier());
        if (request.getCalendar() != null) {
            dynamic.setExpiracyQuantity(request.getCalendar().getExpiracy());
            // dynamic.setDueDate(request.getCalendar().getDue());
            dynamic.setReceivableAfterMaturity(request.getCalendar().isReceivableAfterMaturity());
        }
        if (request.getDebtor() != null) {
            dynamic.setCpf(request.getDebtor().getCpf());
            dynamic.setCnpj(request.getDebtor().getCnpj());
            dynamic.setName(request.getDebtor().getName());
        }
        if (request.getReceiver() != null) {
            dynamic.setReceiverName(request.getReceiver().getName());
        }
        if (request.getValue() != null) {
            dynamic.setOriginalValue(request.getValue().getOriginal());
            dynamic.setFinalValue(request.getValue().getFinale());
            dynamic.setInterestValue(request.getValue().getInterest());
            dynamic.setPenaltyValue(request.getValue().getPenalty());
            dynamic.setDiscountValue(request.getValue().getDiscount());
            //dynamic.setAllowsChange(request.getValue().isAllowsChange());
        }
        dynamic.setPayerRequest(request.getPayerRequest());
        dynamic.setId(UUID.randomUUID());
        log.info(dynamic.toString());
        dynamicQrCodeRepository.save(dynamic);

        if (!CollectionUtils.isEmpty(request.getAdditionalInformations())) {
            for (DynamicQrCodeRequest.AdditionalInformationRequest itemInfo : request.getAdditionalInformations()) {
                AdditionalInformation newInfo = new AdditionalInformation();
                newInfo.setName(itemInfo.getName());
                newInfo.setValue(itemInfo.getValue());
                newInfo.setDynamicQrCode(dynamic);
                additionalInformationRepository.save(newInfo);
            }

        }
        code = dynamicQRCodeBuilderFactory.buildQRCodeString(dynamic);

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
