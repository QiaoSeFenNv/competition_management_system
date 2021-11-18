package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    String userId;
    String userName;
    String avatar;
    String desc;
    String token;
    String home;
    List roles = new ArrayList();


}
