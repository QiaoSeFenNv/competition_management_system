package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@Data
@NoArgsConstructor
public class UserDto {
    String userId;
    String userName;
    String avatar;
    String desc;
    String token;
    String home;
    HashMap<String,String> roles = new HashMap<>();


}
