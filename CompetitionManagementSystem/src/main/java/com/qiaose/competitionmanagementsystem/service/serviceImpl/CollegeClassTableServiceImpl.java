package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CollegeClassTableMapper;
import com.qiaose.competitionmanagementsystem.entity.CollegeClassTable;
import com.qiaose.competitionmanagementsystem.service.CollegeClassTableService;

import java.util.List;

@Service
public class CollegeClassTableServiceImpl implements CollegeClassTableService{

    @Resource
    private CollegeClassTableMapper collegeClassTableMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return collegeClassTableMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CollegeClassTable record) {
        return collegeClassTableMapper.insert(record);
    }

    @Override
    public int insertSelective(CollegeClassTable record) {
        return collegeClassTableMapper.insertSelective(record);
    }

    @Override
    public CollegeClassTable selectByPrimaryKey(Long id) {
        return collegeClassTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CollegeClassTable record) {
        return collegeClassTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CollegeClassTable record) {
        return collegeClassTableMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CollegeClassTable> getAllClass() {
        return collegeClassTableMapper.getAllClass();
    }

}
