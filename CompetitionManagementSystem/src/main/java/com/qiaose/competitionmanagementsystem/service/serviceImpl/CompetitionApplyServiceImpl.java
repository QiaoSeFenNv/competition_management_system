package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionApplyMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionApply;
import com.qiaose.competitionmanagementsystem.service.CompetitionApplyService;

import java.util.List;

@Service
public class CompetitionApplyServiceImpl implements CompetitionApplyService{

    @Resource
    private CompetitionApplyMapper competitionApplyMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return competitionApplyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionApply record) {
        return competitionApplyMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionApply record) {
        return competitionApplyMapper.insertSelective(record);
    }

    @Override
    public CompetitionApply selectByPrimaryKey(Long id) {
        return competitionApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionApply record) {
        return competitionApplyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionApply record) {
        return competitionApplyMapper.updateByPrimaryKey(record);
    }

    @Override
    public CompetitionApply selectByUserIdAndInfoId(Long infoId, String userId) {
        return competitionApplyMapper.selectByUserIdAndInfoId(infoId, userId);
    }

    @Override
    public List<CompetitionApply> selectByUserId(String userId) {
        return competitionApplyMapper.selectByUserId(userId);
    }

}
