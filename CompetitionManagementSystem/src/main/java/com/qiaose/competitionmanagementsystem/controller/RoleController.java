package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;
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

    @GetMapping("/getAllRoles")
    @ApiOperation(value = "返回所有角色", notes = "不需要发送任何请求")
    public R getAllRoles() {
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();
        return R.ok(sysRoleTables);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "插入一条角色信息", notes = "前端需要插入角色body,不需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R InsertRole(@RequestBody SysRoleTable sysRoleTable) {

        if (sysRoleTable == null || StringUtil.isNullOrEmpty(sysRoleTable.getRoleName())
                || StringUtil.isNullOrEmpty(sysRoleTable.getDescription())) {
            return R.failed("信息不全");
        }

        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();
        for (SysRoleTable roleTable : sysRoleTables) {
            if (roleTable.getRoleName().equals(sysRoleTable.getRoleName())) {
                return R.failed("角色名称重复，无法插入");
            }
        }

        sysRoleTable.setRoleId(String.valueOf(IDUtils.CreateId()));
        int i = sysRoleTableService.insertSelective(sysRoleTable);
        if (i <= 0) {
            return R.failed("插入失败");
        }

        return R.ok("插入成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新一条角色信息", notes = "前端需要插入角色body,需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R UpdateRole(@RequestBody SysRoleTable sysRoleTable) {

        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();
        for (SysRoleTable roleTable : sysRoleTables) {
            if (roleTable.getRoleName().equals(sysRoleTable.getRoleName())) {
                return R.failed("角色名称重复，无法插入");
            }
        }


        int i = sysRoleTableService.updateByPrimaryKeySelective(sysRoleTable);
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

}
