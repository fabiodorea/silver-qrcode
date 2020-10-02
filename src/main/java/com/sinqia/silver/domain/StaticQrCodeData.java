package com.sinqia.silver.domain;

import lombok.Data;

@Data
public class StaticQrCodeData {

    QrCodeField payloadFormatIndicator = new QrCodeField("00", "Payload Format Indicator", 2, "01", null, null, false);
    QrCodeField pointOfInitiationMethod = new QrCodeField("01", "Point of Initiation Method", 2, "11", null, null, false); // “11” (QR reutilizável) ou “12” (QR utilizável apenas uma vez)
    QrCodeField merchantAccountInformation = new QrCodeField("26", "Merchant Account Information", 0, "", null, null, true);
    QrCodeField gui = new QrCodeField("00", "GUI", 14, "br.gov.bcb.pix", 0, 0, false);
    QrCodeField key = new QrCodeField("01", "Chave", 0, "", 1, 77, true);
    QrCodeField additionalInfo = new QrCodeField("02", "Aditional Information", 0, "", 1, 72, true);
    QrCodeField merchantCategoryCode = new QrCodeField("52", "Merchant Category Code", 4, "0000", null, null, false);
    QrCodeField transactionCurrency = new QrCodeField("53", "Transaction Currency", 3, "986", null, null, false);
    QrCodeField transactionAmount = new QrCodeField("54", "Transaction Amout", 0, "", 1, 13, true);
    QrCodeField countryCode = new QrCodeField("58", "Country Code", 2, "BR", null, null, false);
    QrCodeField merchantName = new QrCodeField("59", "Merchant Name", 0, "", null, null, true);
    QrCodeField merchantCity = new QrCodeField("60", "Merchant City", 0, "", null, null, true);
    QrCodeField additionalDataField = new QrCodeField("62", "Aditional Data Field", 0, "", 1, 99, true);
    QrCodeField referenceLabel = new QrCodeField("05", "Reference Label", 0, "", 1, 25, true);
    QrCodeField crc16 = new QrCodeField("63", "Merchant City", 4, "DFE3", null, null, true);
}
