package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.CompetitionLog;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionLogMapper;
import com.qiaose.competitionmanagementsystem.service.CompetitionLogService;
@Service
public class CompetitionLogServiceImpl implements CompetitionLogService{

    @Resource
    private CompetitionLogMapper competitionLogMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return competitionLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionLog record) {
        return competitionLogMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionLog record) {
        return competitionLogMapper.insertSelective(record);
    }



    @Override
    public CompetitionLog selectByPrimaryKey(Integer id) {
        return competitionLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionLog record) {
        return competitionLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionLog record) {
        return competitionLogMapper.updateByPrimaryKey(record);
    }

}
