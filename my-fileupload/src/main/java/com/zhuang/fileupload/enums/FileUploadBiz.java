package com.zhuang.fileupload.enums;

import java.util.Arrays;

public enum FileUploadBiz {

    sys_user$pic("sys_user", "pic", "用户表.头像");

    FileUploadBiz(String table, String field, String description) {
        this.table = table;
        this.field = field == null ? "" : field;
        this.value = this.table + TABLE_FIELD_SEPARATOR + this.field;
        this.description = description;
    }

    private static final String TABLE_FIELD_SEPARATOR = ".";

    private String table;
    private String field;
    private String value;
    private String description;

    public String getTable(){
        return this.table;
    }

    public String getField(){
        return this.field;
    }

    public String getValue(){
        return this.value;
    }

    public static FileUploadBiz getByValue(String value) {
        return Arrays.stream(FileUploadBiz.values()).filter(c -> c.getValue().equals(value)).findFirst().orElse(null);
    }
}
