package com.sinqia.silver.factory;

import com.sinqia.silver.domain.DynamicQrCodeData;
import com.sinqia.silver.domain.QrCodeField;
import com.sinqia.silver.entity.DynamicQrCode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.Locale;

@Component
public class DynamicQRCodeBuilderFactory {

    @Value("${pix.dynamic-json.path}")
    private String dynamicJsonPath;

    @Value("${pix.host}")
    private String host;

    private static NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

    static {
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setMinimumIntegerDigits(1);
        format.setGroupingUsed(false);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String buildQRCodeString(DynamicQrCode data) {
        StringBuilder code = new StringBuilder();
        DynamicQrCodeData qrCodeData = new DynamicQrCodeData();

        String financialValue = format.format(data.getFinalValue());
        qrCodeData.getTransactionAmount().setValue(format.format(data.getFinalValue()));
        qrCodeData.getTransactionAmount().setSize(financialValue.length());

        qrCodeData.getMerchantName().setValue(data.getReceiverName());
        qrCodeData.getMerchantName().setSize(data.getReceiverName().length());

        qrCodeData.getMerchantCity().setValue(data.getCity());
        qrCodeData.getMerchantCity().setSize(data.getCity().length());

        return code
                .append(qrCodeData.getPayloadFormatIndicator().toString())
                .append(qrCodeData.getPointOfInitiationMethod().toString())
                .append(merchantAccountInfoCalculate(qrCodeData, data))
                .append(qrCodeData.getMerchantCategoryCode().toString())
                .append(qrCodeData.getTransactionCurrency().toString())
                .append(qrCodeData.getTransactionAmount().getSize() > 0 ?  qrCodeData.getTransactionAmount().toString() : null)
                .append(qrCodeData.getCountryCode().toString())
                .append(qrCodeData.getMerchantName().toString())
                .append(qrCodeData.getMerchantCity().toString())
                .append(additionalDataFieldCalculate(qrCodeData, data))
                .append(qrCodeData.getCrc16().toString())
                .toString();
    }

    private String merchantAccountInfoCalculate(DynamicQrCodeData qrCodeData, DynamicQrCode data){
        QrCodeField merchantInfo = qrCodeData.getMerchantAccountInformation();
        QrCodeField gui = qrCodeData.getGui();
        QrCodeField url = qrCodeData.getUrl();

        url.setValue(host + dynamicJsonPath + data.getId());
        url.setSize(url.getValue().length());

        merchantInfo.setValue(gui.toString() + url.toString());
        merchantInfo.setSize(gui.toString().length() + url.toString().length());

        return merchantInfo.toString();
    }

    private String additionalDataFieldCalculate(DynamicQrCodeData qrCodeData, DynamicQrCode data){
        QrCodeField additionalDataField = qrCodeData.getAdditionalDataField();
        QrCodeField referenceLabel = qrCodeData.getReferenceLabel();

        if(!Strings.isEmpty(data.getTransactionIdentifier())){
            referenceLabel.setValue(data.getTransactionIdentifier());
            referenceLabel.setSize(data.getTransactionIdentifier().length());

            additionalDataField.setValue(referenceLabel.toString());
            additionalDataField.setSize(referenceLabel.toString().length());
            return additionalDataField.toString();
        }
        return "";
    }

}
