package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionBonusMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionBonus;
import com.qiaose.competitionmanagementsystem.service.CompetitionBonusService;
@Service
public class CompetitionBonusServiceImpl implements CompetitionBonusService{

    @Resource
    private CompetitionBonusMapper competitionBonusMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return competitionBonusMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionBonus record) {
        return competitionBonusMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionBonus record) {
        return competitionBonusMapper.insertSelective(record);
    }

    @Override
    public CompetitionBonus selectByPrimaryKey(Long id) {
        return competitionBonusMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionBonus record) {
        return competitionBonusMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionBonus record) {
        return competitionBonusMapper.updateByPrimaryKey(record);
    }

}
