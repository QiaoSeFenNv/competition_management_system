package com.qiaose.competitionmanagementsystem.service.serviceImpl.adminServiceImpl;

import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.adminMapper.SysRoleFrontendMenuTableMapper;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleFrontendMenuTableService;

import java.util.List;

@Service
public class SysRoleFrontendMenuTableServiceImpl implements SysRoleFrontendMenuTableService{

    @Resource
    private SysRoleFrontendMenuTableMapper sysRoleFrontendMenuTableMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
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
    public SysRoleFrontendMenuTable selectByPrimaryKey(Long id) {
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
    public List<SysRoleFrontendMenuTable> selectByRoleId(String roleId) {
        return sysRoleFrontendMenuTableMapper.selectByRoleId(roleId);
    }


    @Override
    public int insertRoleMenu(String roleId, Long menu) {
        SysRoleFrontendMenuTable sysRoleFrontendMenuTable = new SysRoleFrontendMenuTable();

        sysRoleFrontendMenuTable.setId(IDUtils.CreateId());
        sysRoleFrontendMenuTable.setAuthorityId(menu);
        sysRoleFrontendMenuTable.setAuthorityType("MENU");
        sysRoleFrontendMenuTable.setRoleId(roleId);

        int i = sysRoleFrontendMenuTableMapper.insertSelective(sysRoleFrontendMenuTable);

        return i;
    }

    @Override
    public int deleteByRoleId(String roleId) {
        return sysRoleFrontendMenuTableMapper.deleteByRoleId(roleId);
    }

    @Override
    public int deleteByAuthorityId(Long id) {
        return sysRoleFrontendMenuTableMapper.deleteByAuthorityId(id);
    }

}
