package com.qiaose.competitionmanagementsystem.service.serviceImpl;


import com.qiaose.competitionmanagementsystem.entity.TeacherInfo;
import com.qiaose.competitionmanagementsystem.mapper.TeacherInfoMapper;
import com.qiaose.competitionmanagementsystem.service.TeacherInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Resource
    private TeacherInfoMapper teacherInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return teacherInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TeacherInfo record) {
        return teacherInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(TeacherInfo record) {
        return teacherInfoMapper.insertSelective(record);
    }

    @Override
    public TeacherInfo selectByPrimaryKey(Integer id) {
        return teacherInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TeacherInfo record) {
        return teacherInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TeacherInfo record) {
        return teacherInfoMapper.updateByPrimaryKey(record);
    }

}
