package com.qiaose.competitionmanagementsystem.enums;

public enum RecordTypeEnum {

    VERIFY("已验证",(byte) 1),
    MANUAL_IMPORT("手动导入",(byte) 2),
    BATCH_IMPORT("批量导入",(byte) 3);

    private String name;
    private Byte code;

    RecordTypeEnum(String name, Byte code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }
}
