package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.CompetitionSlideshow;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionSlideshowMapper;
import com.qiaose.competitionmanagementsystem.service.CompetitionSlideshowService;

import java.util.List;

@Service
public class CompetitionSlideshowServiceImpl implements CompetitionSlideshowService{

    @Resource
    private CompetitionSlideshowMapper competitionSlideshowMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return competitionSlideshowMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompetitionSlideshow record) {
        return competitionSlideshowMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionSlideshow record) {
        return competitionSlideshowMapper.insertSelective(record);
    }

    @Override
    public CompetitionSlideshow selectByPrimaryKey(Integer id) {
        return competitionSlideshowMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionSlideshow record) {
        return competitionSlideshowMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionSlideshow record) {
        return competitionSlideshowMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CompetitionSlideshow> selectAll() {
        return competitionSlideshowMapper.selectAll();
    }

}
