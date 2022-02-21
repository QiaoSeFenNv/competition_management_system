package com.qiaose.competitionmanagementsystem.enums;

import lombok.Data;

/**
 * @ClassName: TodoStateEnum
 * @Description:
 * @Author qiaosefennv
 * @Date 2022/2/20
 * @Version 1.0
 */

public enum TodoStateEnum {
    NOT_START ("未开始",(byte) 0),
    IN_PROGRESS ("进行中",(byte) 1),
    FINISH("完成",(byte) 2),
    CLOSE("关闭",(byte) 3),
    AGREE("同意",(byte)4),
    DISAGREE("拒绝",(byte)5);

    private String name;
    private Byte code;

    TodoStateEnum(String name, Byte code) {
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
