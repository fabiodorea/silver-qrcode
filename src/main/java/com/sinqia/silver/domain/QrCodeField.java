package com.sinqia.silver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeField {

    private String id;
    private String name;
    private int size;
    private String value;
    private Integer minSize;
    private Integer maxSize;
    private boolean calculated;

    @Override
    public String toString() {
        return id + getSize(size) + value;
    }

    private String getSize(int value) {
        return String.format("%02d", value);
    }

}
