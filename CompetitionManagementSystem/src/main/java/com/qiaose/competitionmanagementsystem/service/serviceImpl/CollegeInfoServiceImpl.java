package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CollegeInfoMapper;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
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

}
