package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CollegeInfoMapper;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;

import java.util.List;

@Service
public class CollegeInfoServiceImpl implements CollegeInfoService{

    @Resource
    private CollegeInfoMapper collegeInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return collegeInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CollegeInfo record) {
        return collegeInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(CollegeInfo record) {
        return collegeInfoMapper.insertSelective(record);
    }

    @Override
    public CollegeInfo selectByPrimaryKey(Integer id) {
        return collegeInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CollegeInfo record) {
        return collegeInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CollegeInfo record) {
        return collegeInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CollegeInfo> findByName(String collegeName) {
        return collegeInfoMapper.findByName(collegeName);
    }



    @Override
    public CollegeInfo selectByName(String collegeName) {
        return collegeInfoMapper.selectByName(collegeName);
    }

    @Override
    public List<CollegeInfo> selectAll() {
        return collegeInfoMapper.selectAll();
    }

}
