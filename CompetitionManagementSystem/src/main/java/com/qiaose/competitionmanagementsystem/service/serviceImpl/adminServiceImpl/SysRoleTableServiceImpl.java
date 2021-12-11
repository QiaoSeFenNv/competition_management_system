package com.qiaose.competitionmanagementsystem.service.serviceImpl.adminServiceImpl;


import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.dto.SysRoleDto;
import com.qiaose.competitionmanagementsystem.mapper.adminMapper.SysRoleTableMapper;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleTableService;
import io.netty.util.internal.StringUtil;
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

    @Override
    public SysRoleTable R_PoToDto(SysRoleDto sysRoleDto) {
        SysRoleTable sysRoleTable = new SysRoleTable();

        if (!StringUtil.isNullOrEmpty(sysRoleDto.getRoleId()))
            sysRoleTable.setRoleId(sysRoleDto.getRoleId());

        if (!StringUtil.isNullOrEmpty( sysRoleDto.getRoleName()))
            sysRoleTable.setRoleName(sysRoleDto.getRoleName());

        if (!StringUtil.isNullOrEmpty( sysRoleDto.getDescription()))
            sysRoleTable.setDescription(sysRoleDto.getDescription());

        return sysRoleTable;
    }

    @Override
    public SysRoleTable selectByName(String role) {
        return sysRoleTableMapper.selectByName(role);
    }

    @Override
    public List<SysRoleTable> selectFindName(String roleName) {
        return sysRoleTableMapper.selectFindName(roleName);
    }

}
