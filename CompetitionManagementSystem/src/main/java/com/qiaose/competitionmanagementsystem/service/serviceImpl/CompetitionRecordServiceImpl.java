package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.qiaose.competitionmanagementsystem.entity.dto.AwardCompetitionDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionRecordMapper;
import com.qiaose.competitionmanagementsystem.service.CompetitionRecordService;

import java.util.List;

@Service
public class CompetitionRecordServiceImpl implements CompetitionRecordService {

    @Resource
    private CompetitionRecordMapper competitionRecordMapper;

    @Override
    public int deleteByPrimaryKey(Long recordId) {
        return competitionRecordMapper.deleteByPrimaryKey(recordId);
    }

    @Override
    public int insert(CompetitionRecord record) {
        return competitionRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionRecord record) {
        return competitionRecordMapper.insertSelective(record);
    }

    @Override
    public CompetitionRecord selectByPrimaryKey(Long recordId) {
        return competitionRecordMapper.selectByPrimaryKey(recordId);
    }

    @Override
    public List<CompetitionRecord> selectAll() {
        return competitionRecordMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionRecord record) {
        return competitionRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionRecord record) {
        return competitionRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionRecord> selectByUserId(String userId) {
        return competitionRecordMapper.selectByUserId(userId);
    }

    @Override
    public List<AwardCompetitionDto> getTotalData() {
        return competitionRecordMapper.getTotalData();
    }

    @Override
    public List<CompetitionRecord> selectByWinningStudent(String userId) {
        return competitionRecordMapper.selectByWinningStudent(userId);
    }

}
