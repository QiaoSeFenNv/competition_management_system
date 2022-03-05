package com.qiaose.competitionmanagementsystem.enums;

public enum BonusTypeEnum {
    NOT_START ("未开始",(byte) 0),
    IN_PROGRESS ("进行中",(byte) 1),
    FILLED("已填写",(byte)2),
    FINISH("完成",(byte) 3);


    private String name;
    private Byte code;

    BonusTypeEnum(String name, Byte code) {
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
