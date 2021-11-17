package com.qiaose.competitionmanagementsystem.service.serviceImpl;


import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.mapper.SysFrontendMenuTableMapper;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysFrontendMenuTableServiceImpl implements SysFrontendMenuTableService {

    @Resource
    private SysFrontendMenuTableMapper sysFrontendMenuTableMapper;

    @Override
    public int deleteByPrimaryKey(String frontendMenuId) {
        return sysFrontendMenuTableMapper.deleteByPrimaryKey(frontendMenuId);
    }

    @Override
    public int insert(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.insertSelective(record);
    }

    @Override
    public List<SysFrontendMenuTable> selectByPrimaryKey(String frontendMenuId) {
        return sysFrontendMenuTableMapper.selectByPrimaryKey(frontendMenuId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysFrontendMenuTable record) {
        return sysFrontendMenuTableMapper.updateByPrimaryKey(record);
    }

}
