package com.qiaose.competitionmanagementsystem.service.serviceImpl.adminServiceImpl;


import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleBackendApiTable;
import com.qiaose.competitionmanagementsystem.mapper.adminMapper.SysRoleBackendApiTableMapper;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleBackendApiTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleBackendApiTableServiceImpl implements SysRoleBackendApiTableService {

    @Resource
    private SysRoleBackendApiTableMapper sysRoleBackendApiTableMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysRoleBackendApiTableMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRoleBackendApiTable record) {
        return sysRoleBackendApiTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRoleBackendApiTable record) {
        return sysRoleBackendApiTableMapper.insertSelective(record);
    }

    @Override
    public SysRoleBackendApiTable selectByPrimaryKey(Integer id) {
        return sysRoleBackendApiTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRoleBackendApiTable record) {
        return sysRoleBackendApiTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRoleBackendApiTable record) {
        return sysRoleBackendApiTableMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysRoleBackendApiTable> selectByRoleId(String roleId) {
        return sysRoleBackendApiTableMapper.selectByRoleId(roleId);
    }

    @Override
    public int deleteByBackApiId(String backendApiId) {
        return sysRoleBackendApiTableMapper.deleteByBackApiId(backendApiId);
    }

}
