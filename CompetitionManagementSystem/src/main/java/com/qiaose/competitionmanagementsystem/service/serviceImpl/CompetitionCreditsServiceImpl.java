package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.CompetitionCredits;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionCreditsMapper;
import com.qiaose.competitionmanagementsystem.service.CompetitionCreditsService;

import java.util.List;

@Service
public class CompetitionCreditsServiceImpl implements CompetitionCreditsService{

    @Resource
    private CompetitionCreditsMapper competitionCreditsMapper;

    @Override
    public int deleteByPrimaryKey(Integer creditId) {
        return competitionCreditsMapper.deleteByPrimaryKey(creditId);
    }

    @Override
    public int insert(CompetitionCredits record) {
        return competitionCreditsMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionCredits record) {
        return competitionCreditsMapper.insertSelective(record);
    }

    @Override
    public CompetitionCredits selectByPrimaryKey(Integer creditId) {
        return competitionCreditsMapper.selectByPrimaryKey(creditId);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionCredits record) {
        return competitionCreditsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionCredits record) {
        return competitionCreditsMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionCredits> getAllCredit() {
        return competitionCreditsMapper.getAllCredit();
    }

}
