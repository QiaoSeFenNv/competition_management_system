package com.qiaose.competitionmanagementsystem.service.serviceImpl.adminServiceImpl;


import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.mapper.adminMapper.SysRoleUserTableMapper;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleUserTableServiceImpl implements SysRoleUserTableService {

    @Resource
    private SysRoleUserTableMapper sysRoleUserTableMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysRoleUserTableMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRoleUserTable record) {
        return sysRoleUserTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRoleUserTable record) {
        return sysRoleUserTableMapper.insertSelective(record);
    }

    @Override
    public SysRoleUserTable selectByPrimaryKey(Integer id) {
        return sysRoleUserTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysRoleUserTable> selectByUserId(String userId) {
        return sysRoleUserTableMapper.selectByUserId(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRoleUserTable record) {
        return sysRoleUserTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRoleUserTable record) {
        return sysRoleUserTableMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByUserId(String id) {
        return sysRoleUserTableMapper.deleteByUserId(id);
    }

}
