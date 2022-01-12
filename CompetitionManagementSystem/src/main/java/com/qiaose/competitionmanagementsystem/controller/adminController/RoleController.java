package com.qiaose.competitionmanagementsystem.controller.adminController;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.SysRoleDto;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("角色管理")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    SysRoleTableService sysRoleTableService;


    @Autowired
    SysRoleFrontendMenuTableService sysRoleFrontendMenuTableService;

    @Resource
    SysRoleFrontendMenuTableService SysRoleFrontendMenuTableService;


    @GetMapping("/getAllRoles")
    @ApiOperation(value = "返回所有角色", notes = "不需要发送任何请求")
    public R getAllRoles(@RequestParam(defaultValue = "1", value = "page") Integer page,
                         @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
    ) {
        PageHelper.startPage(page,pageSize);
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectAll();
        sysRoleTables.forEach(sysRoleTable->{
            List<Long>  FrontendMenuId= sysRoleFrontendMenuTableService.selectOutRoleId(sysRoleTable.getRoleId());
            sysRoleTable.setMenu(FrontendMenuId);
        });


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



    @PostMapping("/InsertOrUpdate")
    @ApiOperation(value = "插入或者一条角色信息,与绑定前端菜单", notes = "如果传递的数据中携带id则会更新否则插入,也可用选择不带菜单id,则就会插入角色")
    @Transactional(rollbackFor = Exception.class)
    public R InsertAndUpdate(@RequestBody SysRoleDto sysRoleDto) {
        SysRoleTable sysRoleTable = sysRoleTableService.R_PoToDto(sysRoleDto);
        String RoleId;
        if (sysRoleDto.getRoleId()!=null){
            //更新
            int i = sysRoleTableService.updateByPrimaryKeySelective(sysRoleTable);
            if (sysRoleDto.getMenu() == null){
                return R.ok("");
            }
            RoleId = sysRoleTable.getRoleId();
            //更新就删除roleId相关的数据，再插入
            int q = sysRoleFrontendMenuTableService.deleteByRoleId(RoleId);
            for (Long menu : sysRoleDto.getMenu()) {
                sysRoleFrontendMenuTableService.insertRoleMenu(RoleId,menu);
            }


        }else{
            //插入
            RoleId = IDUtils.CreateId()+"";
            sysRoleTable.setRoleId(RoleId);

            int i = sysRoleTableService.insertSelective(sysRoleTable);
            if (sysRoleDto.getMenu() == null){
                return R.ok("");
            }
            for (Long menu : sysRoleDto.getMenu()) {
                int j = sysRoleFrontendMenuTableService.insertRoleMenu(RoleId, menu);
            }
        }

        return R.ok("插入成功");
    }



    @PostMapping("/delete")
    @ApiOperation(value = "删除一条角色信息", notes = "前端需要插入角色body,携带id")
    @Transactional(rollbackFor = Exception.class)
    public R DeleteRole(@RequestBody SysRoleTable sysRoleTable) {
        int i = sysRoleTableService.deleteByPrimaryKey(sysRoleTable.getRoleId());

        int j = sysRoleFrontendMenuTableService.deleteByRoleId(sysRoleTable.getRoleId());

        if (i <= 0) {
            return R.failed("删除失败");
        }
        return R.ok("删除成功");
    }

}
