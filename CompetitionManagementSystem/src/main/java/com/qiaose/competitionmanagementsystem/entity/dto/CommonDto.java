package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CommonDto
 * @Description: 模板数据返回
 * @Author qiaosefennv
 * @Date 2022/3/5
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class CommonDto {
    private int toDayReceive;
    private int toDayDeal;
    private int sumPrice;
    private int integral;
    private int credits;
}
