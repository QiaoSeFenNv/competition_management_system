package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionInfoMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionInfo;
import com.qiaose.competitionmanagementsystem.service.CompetitionInfoService;

import java.util.List;

@Service
public class CompetitionInfoServiceImpl implements CompetitionInfoService{

    @Resource
    private CompetitionInfoMapper competitionInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return competitionInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionInfo record) {
        return competitionInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionInfo record) {
        return competitionInfoMapper.insertSelective(record);
    }

    @Override
    public CompetitionInfo selectByPrimaryKey(Long id) {
        return competitionInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionInfo record) {
        return competitionInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionInfo record) {
        return competitionInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionInfo> selectByAll() {
        return competitionInfoMapper.selectByAll();
    }

    @Override
    public List<CompetitionInfo> selectByState(Integer state) {
        return competitionInfoMapper.selectByState(state);
    }

    @Override
    public List<CompetitionInfo> selectByName(String name) {
        return competitionInfoMapper.selectByName(name);
    }

}
