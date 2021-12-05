package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionOrganizerMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionOrganizer;
import com.qiaose.competitionmanagementsystem.service.CompetitionOrganizerService;

import java.util.List;

@Service
public class CompetitionOrganizerServiceImpl implements CompetitionOrganizerService{

    @Resource
    private CompetitionOrganizerMapper competitionOrganizerMapper;

    @Override
    public int deleteByPrimaryKey(Integer organizeId) {
        return competitionOrganizerMapper.deleteByPrimaryKey(organizeId);
    }

    @Override
    public int insert(CompetitionOrganizer record) {
        return competitionOrganizerMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionOrganizer record) {
        return competitionOrganizerMapper.insertSelective(record);
    }

    @Override
    public CompetitionOrganizer selectByPrimaryKey(Integer organizeId) {
        return competitionOrganizerMapper.selectByPrimaryKey(organizeId);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionOrganizer record) {
        return competitionOrganizerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionOrganizer record) {
        return competitionOrganizerMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionOrganizer> selectAll() {
        return competitionOrganizerMapper.selectAll();
    }

    @Override
    public List<CompetitionOrganizer> findOrganize(String organize) {
        return competitionOrganizerMapper.findOrganize(organize);
    }

}
