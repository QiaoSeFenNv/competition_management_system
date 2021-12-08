package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.SysRoleDto;
import com.qiaose.competitionmanagementsystem.service.SysRoleFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
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
    SysRoleFrontendMenuTableService sysRoleFrontendMenuTableService;


    @GetMapping("/getAllRoles")
    @ApiOperation(value = "返回所有角色", notes = "不需要发送任何请求")
    public R getAllRoles(@RequestParam(defaultValue = "1", value = "page") Integer page,
                         @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
    ) {
        PageHelper.startPage(page,pageSize);
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();

        PageInfo<SysRoleTable> pageInfo = new PageInfo<>(sysRoleTables);
        List<SysRoleTable> list = pageInfo.getList();

        //封装起来前端需要
        PageDto pageDto = new PageDto();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());
        return R.ok(pageDto);
    }


    @GetMapping("/findRole")
    @ApiOperation(value = "模糊查询返回所有角色", notes = "")
    public R findRole(@RequestParam(defaultValue = "1", value = "page") Integer page,
                    @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
                    @RequestParam(value = "roleName",required = false) String roleName) {
        PageHelper.startPage(page,pageSize);
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectFindName(roleName);

        PageInfo<SysRoleTable> pageInfo = new PageInfo<>(sysRoleTables);
        List<SysRoleTable> list = pageInfo.getList();

        //封装起来前端需要
        PageDto pageDto = new PageDto();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());
        return R.ok(pageDto);
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


        sysRoleTable.setRoleId( IDUtils.CreateId()+"");
        int i = sysRoleTableService.insertSelective(sysRoleTable);
        //以上都是插入一个角色对象

        //中间表

        Long[] menu = sysRoleDto.getMenu();
        SysRoleFrontendMenuTable sysRoleFrontendMenuTable = new SysRoleFrontendMenuTable();
        sysRoleFrontendMenuTable.setId(IDUtils.CreateId());
        sysRoleFrontendMenuTable.setAuthorityType("MENU");
        sysRoleFrontendMenuTable.setRoleId(Long.valueOf(sysRoleDto.getRoleId()));


        for (Long aLong : menu) {
            sysRoleFrontendMenuTable.setAuthorityId(aLong);
            sysRoleFrontendMenuTableService.insertSelective(sysRoleFrontendMenuTable);
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


        InsertRoleFrontMenu(sysRoleDto);


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



    @GetMapping("/binding")
    @ApiOperation(value = "绑定角色与菜单关系", notes = "前端需要传入两个值，role对应角色表中的id，auth对应菜单表id")
    @Transactional(rollbackFor = Exception.class)
    public R RoleBindMenu(@RequestParam Long roleId,
                          @RequestParam Long authId){


        SysRoleFrontendMenuTable sysRoleFrontendMenuTable = new SysRoleFrontendMenuTable();
        sysRoleFrontendMenuTable.setRoleId(roleId);
        sysRoleFrontendMenuTable.setAuthorityId(authId);

        sysRoleFrontendMenuTableService.insertSelective(sysRoleFrontendMenuTable);

        return R.ok("绑定成功");
    }

    public Boolean InsertRoleFrontMenu(SysRoleDto sysRoleDto){

            if (sysRoleDto.getMenu() == null){
                return false;
            }
            Long[] menu = sysRoleDto.getMenu();
            SysRoleFrontendMenuTable sysRoleFrontendMenuTable = new SysRoleFrontendMenuTable();
            sysRoleFrontendMenuTable.setId(IDUtils.CreateId());

            sysRoleFrontendMenuTable.setCreateTime(DateKit.getNow());
            sysRoleFrontendMenuTable.setCreatedBy(Long.valueOf(sysRoleDto.getRoleId()));
            sysRoleFrontendMenuTable.setAuthorityType("MENU");

            sysRoleFrontendMenuTable.setRoleId(Long.valueOf(sysRoleDto.getRoleId()));

            //判断是否重复插入
        List<SysRoleFrontendMenuTable> sysRoleFrontendMenuTables = sysRoleFrontendMenuTableService.selectByRoleId(Long.valueOf(sysRoleDto.getRoleId()));

        for (SysRoleFrontendMenuTable roleFrontendMenuTable : sysRoleFrontendMenuTables) {
            Long authorityId = roleFrontendMenuTable.getAuthorityId();
            for (Long aLong : menu) {
                if (authorityId == aLong) {
                    return false;
                }
            }
        }

        for (Long integer : menu) {
                sysRoleFrontendMenuTable.setAuthorityId(integer);
                sysRoleFrontendMenuTableService.insertSelective(sysRoleFrontendMenuTable);
            }
        return true;
    }


}
