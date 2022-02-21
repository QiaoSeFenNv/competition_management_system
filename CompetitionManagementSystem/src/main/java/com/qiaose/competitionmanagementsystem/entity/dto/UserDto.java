package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class UserDto {
    String userId;
    String avatar;
    String token;
    List roles;
    String userName;


}
