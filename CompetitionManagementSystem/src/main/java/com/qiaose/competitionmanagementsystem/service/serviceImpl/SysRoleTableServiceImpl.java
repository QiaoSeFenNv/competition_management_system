package com.qiaose.competitionmanagementsystem.service.serviceImpl;


import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.mapper.SysRoleTableMapper;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleTableServiceImpl implements SysRoleTableService {

    @Resource
    private SysRoleTableMapper sysRoleTableMapper;

    @Override
    public int deleteByPrimaryKey(String roleId) {
        return sysRoleTableMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(SysRoleTable record) {
        return sysRoleTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRoleTable record) {
        return sysRoleTableMapper.insertSelective(record);
    }

    @Override
    public List<SysRoleTable> selectByPrimaryKey(String roleId) {
        return sysRoleTableMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRoleTable record) {
        return sysRoleTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRoleTable record) {
        return sysRoleTableMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysRoleTable> selectAll() {
        return sysRoleTableMapper.selectAll();
    }

}
