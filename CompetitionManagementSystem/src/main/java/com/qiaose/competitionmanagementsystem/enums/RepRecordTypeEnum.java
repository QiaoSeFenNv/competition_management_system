package com.qiaose.competitionmanagementsystem.enums;

public enum RepRecordTypeEnum {
    NOT_ACTIVE("未激活",(byte) 0),//置换记录尚未开具证明
    UNUSED("未使用",(byte) 1), //置换记录已开具证明，但未进行学分置换
    USED("已使用",(byte) 2), //置换记录已进行过学分置换
    ;
    private String name;
    private Byte code;

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

    RepRecordTypeEnum(String name, Byte code) {
        this.name = name;
        this.code = code;
    }
}
