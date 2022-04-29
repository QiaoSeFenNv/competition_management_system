package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiaose.competitionmanagementsystem.entity.CompetitionCourseRepRecord;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionCourseRepRecordMapper;
import com.qiaose.competitionmanagementsystem.service.ICompetitionCourseRepRecordService;
import org.springframework.stereotype.Service;

@Service
public class CompetitionCourseRepRecordServiceImpl extends ServiceImpl<CompetitionCourseRepRecordMapper, CompetitionCourseRepRecord> implements ICompetitionCourseRepRecordService {

    @Override
    public int add(CompetitionCourseRepRecord competitionCourseRepRecord) {
        return this.baseMapper.insertSelective(competitionCourseRepRecord);
    }
}
