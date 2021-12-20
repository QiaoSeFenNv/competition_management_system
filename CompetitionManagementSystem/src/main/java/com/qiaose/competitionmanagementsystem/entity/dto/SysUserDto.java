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

    private String phone;

    private String deptId;

    private String[] role;
}
