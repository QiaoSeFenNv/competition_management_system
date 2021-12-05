package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;

@Data
public class SysUserDto {

    /**
     * 用于返回多表信息
     */
    private Integer id;
    private String stuId;
    private String name;
    private String email;
    private String phone;
    private String department;

}
