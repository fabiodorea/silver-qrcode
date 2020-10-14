package com.sinqia.silver.service;

import com.sinqia.silver.domain.enums.EnumBoolean;
import com.sinqia.silver.domain.enums.EnumPayerType;
import com.sinqia.silver.entity.AdditionalInformation;
import com.sinqia.silver.entity.DynamicQrCode;
import com.sinqia.silver.entity.Situation;
import com.sinqia.silver.exception.BusinessException;
import com.sinqia.silver.exception.UnableToGenerateQRCodeImageException;
import com.sinqia.silver.factory.DynamicQRCodeBuilderFactory;
import com.sinqia.silver.repository.AdditionalInformationRepository;
import com.sinqia.silver.repository.DynamicQrCodeRepository;
import com.sinqia.silver.repository.SituationRepository;
import com.sinqia.silver.request.DynamicQrCodeRequest;
import com.sinqia.silver.response.DynamicQrCodeResponse;
import com.sinqia.silver.util.ImageUtil;
import com.sinqia.silver.util.ValidationUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
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
    
    @Autowired
    private SituationRepository situationRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public DynamicQrCodeResponse generate(DynamicQrCodeRequest request) {

        DynamicQrCodeResponse response = new DynamicQrCodeResponse();
        String code;
        if (request.getDebtor() != null && !StringUtils.isEmpty(request.getDebtor().getName()) &&
                (StringUtils.isEmpty(request.getDebtor().getCnpj())
                        && StringUtils.isEmpty(request.getDebtor().getCpf()))) {
            throw new BusinessException("Obrigatório informar o cpf ou cnpj ao informar o nome do devedor");
        }
        
        if (request.getDebtor() != null && 
                (!StringUtils.isEmpty(request.getDebtor().getCnpj())
                        && !StringUtils.isEmpty(request.getDebtor().getCpf()))) {
            throw new BusinessException("Informar o cpf ou cnpj do devedor, não os dois");
        }
        
        if (request.getDebtor() != null && !StringUtils.isEmpty(request.getDebtor().getCnpj()) 
                && !ValidationUtil.isValidCNPJ(request.getDebtor().getCnpj())) {
            throw new BusinessException("Cnpj inválido");
        }
        
        if (request.getDebtor() != null && !StringUtils.isEmpty(request.getDebtor().getCpf()) 
                && !ValidationUtil.isValidCPF(request.getDebtor().getCpf())) {
            throw new BusinessException("Cpf inválido");
        }
        
        Situation situation = situationRepository.findByDescription("ATIVA")
                .orElseThrow(() -> new BusinessException("Situação ativa não encontrada!"));

        DynamicQrCode dynamic = new DynamicQrCode();
        dynamic.setKey(request.getKey());
        dynamic.setCity(request.getCity());
        dynamic.setTransactionIdentifier(request.getTransactionIdentifier());
        if (request.getCalendar() != null) {
            dynamic.setExpirationDate(LocalDateTime.now().plusSeconds(request.getCalendar().getExpiracy()));
            dynamic.setDueDate(request.getCalendar().getDue().atStartOfDay());
            dynamic.setReceivableAfterDue(getBooleanValue(request.getCalendar().isReceivableAfterMaturity()));
        }
        if (request.getDebtor() != null) {
            dynamic.setPayerCpfCnpj(getPayerCpfCnpj(request));
            dynamic.setPayerType(getPayerType(request));
            dynamic.setPayerName(request.getDebtor().getName());
        }
        dynamic.setPayerRequest(request.getPayerRequest());
        
        if (request.getReceiver() != null) {
            dynamic.setReceiverName(request.getReceiver().getName());
        }
        if (request.getValue() != null) {
            dynamic.setOriginalValue(request.getValue().getOriginal());
            dynamic.setFinalValue(request.getValue().getFinale());
            dynamic.setInterestValue(request.getValue().getInterest());
            dynamic.setPenaltyValue(request.getValue().getPenalty());
            dynamic.setDiscountValue(request.getValue().getDiscount());
            dynamic.setAllowsChange(getBooleanValue(request.getValue().isAllowsChange()));
        }
        dynamic.setPayloadIdentifier(UUID.randomUUID());
        dynamic.setPayloadVersion("1.0");
        dynamic.setPayloadRevision(1L);
        dynamic.setCreated(LocalDateTime.now());
        dynamic.setPresentation(LocalDateTime.now());
        dynamic.setSituation(situation);
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
            return response;
        } catch (IOException e) {
            throw new UnableToGenerateQRCodeImageException();
        }
    }

    private EnumBoolean getBooleanValue(boolean value) {
        if (value)
            return EnumBoolean.S;
        else
            return EnumBoolean.N;
    }

    private EnumPayerType getPayerType(DynamicQrCodeRequest request) {
        if (request.getDebtor().getCnpj() != null && !StringUtils.isEmpty(request.getDebtor().getCnpj()))
            return EnumPayerType.J;
        else if (request.getDebtor().getCpf() != null && !StringUtils.isEmpty(request.getDebtor().getCpf()))
            return EnumPayerType.F;
        else
            return EnumPayerType.N;
    }
    
    private String getPayerCpfCnpj(DynamicQrCodeRequest request) {
        if (request.getDebtor().getCnpj() != null && request.getDebtor().getCnpj().trim().length() > 0)
            return request.getDebtor().getCnpj();
        else
            return request.getDebtor().getCpf();

    }
}
