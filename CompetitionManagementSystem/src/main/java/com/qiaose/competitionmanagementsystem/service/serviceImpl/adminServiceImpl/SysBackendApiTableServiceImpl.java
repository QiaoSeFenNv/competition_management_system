package com.qiaose.competitionmanagementsystem.service.serviceImpl.adminServiceImpl;


import com.qiaose.competitionmanagementsystem.entity.admin.SysBackendApiTable;
import com.qiaose.competitionmanagementsystem.mapper.adminMapper.SysBackendApiTableMapper;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysBackendApiTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysBackendApiTableServiceImpl implements SysBackendApiTableService {

    @Resource
    private SysBackendApiTableMapper sysBackendApiTableMapper;

    @Override
    public int deleteByPrimaryKey(String backendApiId) {
        return sysBackendApiTableMapper.deleteByPrimaryKey(backendApiId);
    }

    @Override
    public int insert(SysBackendApiTable record) {
        return sysBackendApiTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysBackendApiTable record) {
        return sysBackendApiTableMapper.insertSelective(record);
    }

    @Override
    public SysBackendApiTable selectByPrimaryKey(String backendApiId) {
        return sysBackendApiTableMapper.selectByPrimaryKey(backendApiId);
    }

    @Override
    public List<SysBackendApiTable> selectByAll() {
        return sysBackendApiTableMapper.selectByAll();
    }

    @Override
    public int updateByPrimaryKeySelective(SysBackendApiTable record) {
        return sysBackendApiTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysBackendApiTable record) {
        return sysBackendApiTableMapper.updateByPrimaryKey(record);
    }

}
