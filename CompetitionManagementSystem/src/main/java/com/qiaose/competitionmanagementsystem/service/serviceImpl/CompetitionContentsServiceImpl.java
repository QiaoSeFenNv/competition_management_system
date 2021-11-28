package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionContentsMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionContents;
import com.qiaose.competitionmanagementsystem.service.CompetitionContentsService;

import java.util.List;

@Service
public class CompetitionContentsServiceImpl implements CompetitionContentsService {

    @Resource
    private CompetitionContentsMapper competitionContentsMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return competitionContentsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionContents record) {
        return competitionContentsMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionContents record) {
        return competitionContentsMapper.insertSelective(record);
    }

    @Override
    public CompetitionContents selectByPrimaryKey(Integer id) {
        return competitionContentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionContents record) {
        return competitionContentsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionContents record) {
        return competitionContentsMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionContents> selectALl() {
        return competitionContentsMapper.selectALl();
    }

}

