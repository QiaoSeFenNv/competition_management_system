package com.qiaose.competitionmanagementsystem.entity.dto;

import com.qiaose.competitionmanagementsystem.entity.CompetitionCourseRepRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseRepDetailDto {
    String userId;
    String userName;
    String dept;
    String userClass;
    String leftCredit;
    CompetitionCourseRepRecord repDetail;
}
