package com.qiaose.competitionmanagementsystem.entity.dto;

import com.qiaose.competitionmanagementsystem.entity.SysBackendApiTable;

/**
 * 前端需要显示父级pid、名称
 */
public class SysBackendApiDto extends SysBackendApiTable {
    String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}