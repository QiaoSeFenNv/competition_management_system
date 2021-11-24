package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.StudentInfoMapper;
import com.qiaose.competitionmanagementsystem.entity.StudentInfo;
import com.qiaose.competitionmanagementsystem.service.StudentInfoService;

import java.util.List;

@Service
public class StudentInfoServiceImpl implements StudentInfoService{

    @Resource
    private StudentInfoMapper studentInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return studentInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(StudentInfo record) {
        return studentInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(StudentInfo record) {
        return studentInfoMapper.insertSelective(record);
    }

    @Override
    public StudentInfo selectByPrimaryKey(Integer id) {
        return studentInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(StudentInfo record) {
        return studentInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(StudentInfo record) {
        return studentInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<StudentInfo> selectByDeptId(Integer deptId) {
        return studentInfoMapper.selectByDeptId(deptId);
    }

    @Override
    public List<StudentInfo> selectByAll() {
        return studentInfoMapper.selectByAll();
    }

}
