package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    private Long comp_id;
    private String user_id;
    private String reward_level;
    private Long relate_approval;
}
