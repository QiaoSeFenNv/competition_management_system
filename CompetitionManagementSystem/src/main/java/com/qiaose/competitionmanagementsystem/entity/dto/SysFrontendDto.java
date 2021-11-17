package com.qiaose.competitionmanagementsystem.entity.dto;

import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;

/**
 * 前端需要显示父级pid、名称
 */
public class SysFrontendDto extends SysFrontendMenuTable {

    String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
