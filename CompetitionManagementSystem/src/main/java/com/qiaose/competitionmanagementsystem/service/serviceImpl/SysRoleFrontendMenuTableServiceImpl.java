package com.qiaose.competitionmanagementsystem.service.serviceImpl;


import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.mapper.SysRoleFrontendMenuTableMapper;
import com.qiaose.competitionmanagementsystem.service.SysRoleFrontendMenuTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysRoleFrontendMenuTableServiceImpl implements SysRoleFrontendMenuTableService {

    @Resource
    private SysRoleFrontendMenuTableMapper sysRoleFrontendMenuTableMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysRoleFrontendMenuTableMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRoleFrontendMenuTable record) {
        return sysRoleFrontendMenuTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRoleFrontendMenuTable record) {
        return sysRoleFrontendMenuTableMapper.insertSelective(record);
    }

    @Override
    public SysRoleFrontendMenuTable selectByPrimaryKey(Integer id) {
        return sysRoleFrontendMenuTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRoleFrontendMenuTable record) {
        return sysRoleFrontendMenuTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRoleFrontendMenuTable record) {
        return sysRoleFrontendMenuTableMapper.updateByPrimaryKey(record);
    }

    @Override
    public SysRoleFrontendMenuTable selectByRoleId(String roleId) {
        return sysRoleFrontendMenuTableMapper.selectByRoleId(roleId);
    }

}
