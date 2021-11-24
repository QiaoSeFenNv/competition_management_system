package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.SysRoleDto;
import com.qiaose.competitionmanagementsystem.mapper.SysRoleFrontendMenuTableMapper;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("角色管理")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    SysRoleTableService sysRoleTableService;


    @Autowired
    SysRoleFrontendMenuTableMapper sysRoleFrontendMenuTableMapper;

    @GetMapping("/getAllRoles")
    @ApiOperation(value = "返回所有角色", notes = "不需要发送任何请求")
    public R getAllRoles(@RequestParam(defaultValue = "1", value = "page") Integer page,
                         @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
    ) {
        PageHelper.startPage(page,pageSize);
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();

        PageInfo<SysRoleTable> pageInfo = new PageInfo<>(sysRoleTables);
        List<SysRoleTable> list = pageInfo.getList();
        return R.ok(list);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "插入一条角色信息", notes = "前端需要插入角色body,不需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R InsertRole(@RequestBody SysRoleDto sysRoleDto) {
        SysRoleTable sysRoleTable = sysRoleTableService.R_PoToDto(sysRoleDto);
        if (sysRoleTable == null || StringUtil.isNullOrEmpty(sysRoleTable.getRoleName())
                || StringUtil.isNullOrEmpty(sysRoleTable.getDescription())) {
            return R.failed("信息不全");
        }
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();
        for (SysRoleTable roleTable : sysRoleTables) {
            if (roleTable.getRoleName().equals(sysRoleTable.getRoleName())
                    && roleTable.getRoleId().equals(sysRoleTable.getRoleId())
            ) {
                return R.failed("角色名称重复，无法插入");
            }
        }
        sysRoleTable.setRoleId(String.valueOf(IDUtils.CreateId()));
        int i = sysRoleTableService.insertSelective(sysRoleTable);
        //中间表
        try {
            InsertRoleFrontMenu(sysRoleDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i <= 0) {
            return R.failed("插入失败");
        }
        return R.ok("插入成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新一条角色信息", notes = "前端需要插入角色body,需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R UpdateRole(@RequestBody SysRoleDto sysRoleDto) {
        SysRoleTable sysRoleTable = sysRoleTableService.R_PoToDto(sysRoleDto);
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();
        for (SysRoleTable roleTable : sysRoleTables) {
            if (roleTable.getRoleName().equals(sysRoleTable.getRoleName())
                 && !roleTable.getRoleId().equals(sysRoleTable.getRoleId())
            ) {
                return R.failed("角色名称重复，无法插入");
            }
        }
        int i = sysRoleTableService.updateByPrimaryKeySelective(sysRoleTable);
        //中间表
        try {
            InsertRoleFrontMenu(sysRoleDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i <= 0) {
            return R.failed("更新失败");
        }
        return R.ok("更新成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除一条角色信息", notes = "前端需要插入角色body,携带id")
    @Transactional(rollbackFor = Exception.class)
    public R DeleteRole(@RequestBody SysRoleTable sysRoleTable) {
        int i = sysRoleTableService.deleteByPrimaryKey(sysRoleTable.getRoleId());
        if (i <= 0) {
            return R.failed("删除失败");
        }
        return R.ok("删除成功");
    }


    public Boolean InsertRoleFrontMenu(SysRoleDto sysRoleDto) throws Exception{
        try {
            if (sysRoleDto.getMenu() == null){
                return false;
            }
            Integer[] menu = sysRoleDto.getMenu();
            SysRoleFrontendMenuTable sysRoleFrontendMenuTable = new SysRoleFrontendMenuTable();
            sysRoleFrontendMenuTable.setRoleId(IDUtils.CreateId());
            sysRoleFrontendMenuTable.setCreateTime(DateKit.getNow());
            sysRoleFrontendMenuTable.setCreatedBy(Long.valueOf(sysRoleDto.getRoleId()));
            sysRoleFrontendMenuTable.setAuthorityType("MENU");
            sysRoleFrontendMenuTable.setRoleId(Long.valueOf(sysRoleDto.getRoleId()));
            for (Integer integer : menu) {
                sysRoleFrontendMenuTable.setAuthorityId(Long.valueOf(integer));
                sysRoleFrontendMenuTableMapper.insertSelective(sysRoleFrontendMenuTable);
            }
        }catch (Exception exception){
            throw new Exception();
        }
        return true;
    }


}
