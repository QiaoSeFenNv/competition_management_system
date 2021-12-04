package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionRewardMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionReward;
import com.qiaose.competitionmanagementsystem.service.CompetitionRewardService;

import java.util.List;

@Service
public class CompetitionRewardServiceImpl implements CompetitionRewardService{

    @Resource
    private CompetitionRewardMapper competitionRewardMapper;

    @Override
    public int deleteByPrimaryKey(Long rewardId) {
        return competitionRewardMapper.deleteByPrimaryKey(rewardId);
    }

    @Override
    public int insert(CompetitionReward record) {
        return competitionRewardMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionReward record) {
        return competitionRewardMapper.insertSelective(record);
    }

    @Override
    public CompetitionReward selectByPrimaryKey(Long rewardId) {
        return competitionRewardMapper.selectByPrimaryKey(rewardId);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionReward record) {
        return competitionRewardMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionReward record) {
        return competitionRewardMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionReward> selectAll() {
        return competitionRewardMapper.selectAll();
    }

}
