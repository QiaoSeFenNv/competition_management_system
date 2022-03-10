package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SysUserDto {
    private Long id;

    private String userId;

    private String userName;

    private String email;

    private String telephone;

    private String deptId;

    private String deptName;

    private String[] role;

    private String remark;

    private String userStatus;
}
