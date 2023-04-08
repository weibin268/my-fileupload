package com.zhuang.fileupload.enums;

import java.util.Arrays;

public enum StoreProviderType {
    FTP("ftp"),
    LOCAL("local"),
    WEB_DAV("webDav");

    private String value;

    StoreProviderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static StoreProviderType getByValue(Integer value) {
        return Arrays.stream(StoreProviderType.values()).filter(c -> c.getValue().equals(value)).findFirst().orElse(null);
    }
}
