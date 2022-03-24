package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CreditDto
 * @Description:
 * @Author qiaosefennv
 * @Date 2022/3/24
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class CreditDto {

    private String userId;

    private Integer creditsEarned;

    private Integer creditsRemain;

}
