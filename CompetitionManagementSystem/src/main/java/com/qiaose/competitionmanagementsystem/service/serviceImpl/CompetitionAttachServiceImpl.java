package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionAttachMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionAttach;
import com.qiaose.competitionmanagementsystem.service.CompetitionAttachService;

import java.util.List;

@Service
public class CompetitionAttachServiceImpl implements CompetitionAttachService{

    @Resource
    private CompetitionAttachMapper competitionAttachMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return competitionAttachMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionAttach record) {
        return competitionAttachMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionAttach record) {
        return competitionAttachMapper.insertSelective(record);
    }

    @Override
    public List<CompetitionAttach> selectByPrimaryKey(Integer id) {
        return competitionAttachMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionAttach record) {
        return competitionAttachMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionAttach record) {
        return competitionAttachMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByIdTime(Integer userId, Integer created,String ftype) {
        return competitionAttachMapper.deleteByIdTime( userId, created,ftype);
    }

    @Override
    public List<CompetitionAttach> selectByPrimaryUserid(Integer id) {
        return competitionAttachMapper.selectByPrimaryUserid(id);
    }

}
