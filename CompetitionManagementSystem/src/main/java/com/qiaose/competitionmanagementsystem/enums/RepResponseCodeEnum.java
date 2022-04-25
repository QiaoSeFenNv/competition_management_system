package com.qiaose.competitionmanagementsystem.enums;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

public enum RepResponseCodeEnum implements IErrorCode {

    ALREADY_IN_USE_OR_NOT_ACTIVATED(0, "该记录已被使用或未激活"),
    RECORD_NOT_FOUND(0, "没有找到该记录"),
    RECORD_VALID(0, "记录有效"),
    ;

    /**
     * 错误码
     */
    private long code;

    /**
     * 错误描述
     */
    private String msg;


    RepResponseCodeEnum(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
