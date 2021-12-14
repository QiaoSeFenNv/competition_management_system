package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionProgramMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionProgram;
import com.qiaose.competitionmanagementsystem.service.CompetitionProgramService;

import java.util.List;

@Service
public class CompetitionProgramServiceImpl implements CompetitionProgramService{

    @Resource
    private CompetitionProgramMapper competitionProgramMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return competitionProgramMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionProgram record) {
        return competitionProgramMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionProgram record) {
        return competitionProgramMapper.insertSelective(record);
    }

    @Override
    public CompetitionProgram selectByPrimaryKey(Long id) {
        return competitionProgramMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionProgram record) {
        return competitionProgramMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionProgram record) {
        return competitionProgramMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionProgram> selectByApproval(Long approvalId) {
        return competitionProgramMapper.selectByApproval(approvalId);
    }

}
