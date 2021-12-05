package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionProcessMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionProcess;
import com.qiaose.competitionmanagementsystem.service.CompetitionProcessService;
@Service
public class CompetitionProcessServiceImpl implements CompetitionProcessService{

    @Resource
    private CompetitionProcessMapper competitionProcessMapper;

    @Override
    public int deleteByPrimaryKey(Long processId) {
        return competitionProcessMapper.deleteByPrimaryKey(processId);
    }

    @Override
    public int insert(CompetitionProcess record) {
        return competitionProcessMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionProcess record) {
        return competitionProcessMapper.insertSelective(record);
    }

    @Override
    public CompetitionProcess selectByPrimaryKey(Long processId) {
        return competitionProcessMapper.selectByPrimaryKey(processId);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionProcess record) {
        return competitionProcessMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionProcess record) {
        return competitionProcessMapper.updateByPrimaryKey(record);
    }

}
