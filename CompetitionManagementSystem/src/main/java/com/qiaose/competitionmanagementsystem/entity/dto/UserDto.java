package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    String userId;
    String avatar;
    String token;
    List roles;
    String userName;
    String phone;
    String email;
    String deptName;


}
