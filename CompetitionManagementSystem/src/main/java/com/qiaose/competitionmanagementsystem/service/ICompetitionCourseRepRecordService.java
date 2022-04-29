package com.qiaose.competitionmanagementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiaose.competitionmanagementsystem.entity.CompetitionCourseRepRecord;

public interface ICompetitionCourseRepRecordService  extends IService<CompetitionCourseRepRecord> {
     int add(CompetitionCourseRepRecord competitionCourseRepRecord);


}
