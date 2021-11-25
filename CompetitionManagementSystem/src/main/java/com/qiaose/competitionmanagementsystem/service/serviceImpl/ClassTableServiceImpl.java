package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.ClassTableMapper;
import com.qiaose.competitionmanagementsystem.entity.ClassTable;
import com.qiaose.competitionmanagementsystem.service.ClassTableService;
@Service
public class ClassTableServiceImpl implements ClassTableService{

    @Resource
    private ClassTableMapper classTableMapper;

    @Override
    public int deleteByPrimaryKey(Integer classId) {
        return classTableMapper.deleteByPrimaryKey(classId);
    }

    @Override
    public int insert(ClassTable record) {
        return classTableMapper.insert(record);
    }

    @Override
    public int insertSelective(ClassTable record) {
        return classTableMapper.insertSelective(record);
    }

    @Override
    public ClassTable selectByPrimaryKey(Integer classId) {
        return classTableMapper.selectByPrimaryKey(classId);
    }

    @Override
    public int updateByPrimaryKeySelective(ClassTable record) {
        return classTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ClassTable record) {
        return classTableMapper.updateByPrimaryKey(record);
    }

}
