package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 用于系统用户权限
 */
@Data
@ToString
public class SysUserDto {
    String id;
    String name;
    String passw;
    String des;

}
