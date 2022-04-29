package com.qiaose.competitionmanagementsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionCourseRepRecord;

public interface CompetitionCourseRepRecordMapper extends BaseMapper<CompetitionCourseRepRecord> {
    public int insertSelective(CompetitionCourseRepRecord competitionCourseRepRecord);


}
