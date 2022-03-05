package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: StudentDto
 * @Description:
 * @Author qiaosefennv
 * @Date 2022/3/3
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String userName;
    private String userId;
}
