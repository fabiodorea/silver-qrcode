package com.sinqia.silver.factory;

import com.sinqia.silver.domain.QrCodeField;
import com.sinqia.silver.domain.StaticQrCodeData;
import com.sinqia.silver.exception.CharacterLimitExceededException;
import com.sinqia.silver.request.DynamicQrCodeRequest;
import com.sinqia.silver.request.StaticQrCodeRequest;
import org.apache.logging.log4j.util.Strings;

import java.text.NumberFormat;
import java.util.Locale;

public class DynamicQRCodeBuilderFactory {

    private static NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

    static {
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setMinimumIntegerDigits(1);
        format.setGroupingUsed(false);
    }

    private static final DynamicQRCodeBuilderFactory INSTANCE = new DynamicQRCodeBuilderFactory();

    public static DynamicQRCodeBuilderFactory getInstance() {
        return INSTANCE;
    }

    public String buildQRCodeString(DynamicQrCodeRequest request) {
        StringBuilder code = new StringBuilder();
        StaticQrCodeData qrCodeData = new StaticQrCodeData();

        /*if(request.getFinancialValue() != null){
            String financialValue = format.format(request.getFinancialValue());
            qrCodeData.getTransactionAmount().setValue(format.format(request.getFinancialValue()));
            qrCodeData.getTransactionAmount().setSize(financialValue.length());
        }*/

        qrCodeData.getMerchantCity().setValue(request.getCity());
        qrCodeData.getMerchantCity().setSize(request.getCity().length());

//        qrCodeData.getMerchantName().setValue(request.getMerchantName());
//        qrCodeData.getMerchantName().setSize(request.getMerchantName().length());

        return code
                .append(qrCodeData.getPayloadFormatIndicator().toString())
                .append(qrCodeData.getPointOfInitiationMethod().toString())
           //     .append(merchantAccountInfoCalculate(qrCodeData, request))
                .append(qrCodeData.getMerchantCategoryCode().toString())
                .append(qrCodeData.getTransactionCurrency().toString())
                .append(qrCodeData.getTransactionAmount().getSize() > 0 ?  qrCodeData.getTransactionAmount().toString() : null)
                .append(qrCodeData.getCountryCode().toString())
                .append(qrCodeData.getMerchantName().toString())
                .append(qrCodeData.getMerchantCity().toString())
             //   .append(additionalDataFieldCalculate(qrCodeData, request))
                .append(qrCodeData.getCrc16().toString())
                .toString();
    }

    private String additionalDataFieldCalculate(StaticQrCodeData qrCodeData, StaticQrCodeRequest request){
        QrCodeField additionalDataField = qrCodeData.getAdditionalDataField();
        QrCodeField referenceLabel = qrCodeData.getReferenceLabel();

        if(!Strings.isEmpty(request.getTransactionIdentifier())){
            referenceLabel.setValue(request.getTransactionIdentifier());
            referenceLabel.setSize(request.getTransactionIdentifier().length());

            additionalDataField.setValue(referenceLabel.toString());
            additionalDataField.setSize(referenceLabel.toString().length());
            return additionalDataField.toString();
        }
        return "";
    }

    private String merchantAccountInfoCalculate(StaticQrCodeData qrCodeData, StaticQrCodeRequest request){
        QrCodeField merchantInfo = qrCodeData.getMerchantAccountInformation();
        QrCodeField gui = qrCodeData.getGui();
        QrCodeField key = qrCodeData.getKey();
        QrCodeField additionalInfo = qrCodeData.getAdditionalInfo();

        key.setValue(request.getKey());
        key.setSize(request.getKey().length());

        merchantInfo.setValue(gui.toString() + key.toString());
        merchantInfo.setSize(key.toString().length() + gui.toString().length());

        if(!Strings.isEmpty(request.getAdicionalInformation())){
            int validAdditionalInfoLength = 99 - (key.getSize() + gui.getSize() + 8);

            if(request.getAdicionalInformation().length() > validAdditionalInfoLength)
                throw new CharacterLimitExceededException("O campo 'informação adicional' ultrapassou o limite permitido.");

            additionalInfo.setValue(request.getAdicionalInformation());
            additionalInfo.setSize(request.getAdicionalInformation().length());

            merchantInfo.setValue(gui.toString() + key.toString() + additionalInfo.toString());
            merchantInfo.setSize(key.toString().length() + gui.toString().length() + additionalInfo.toString().length());
        }

        return merchantInfo.toString();
    }


}
