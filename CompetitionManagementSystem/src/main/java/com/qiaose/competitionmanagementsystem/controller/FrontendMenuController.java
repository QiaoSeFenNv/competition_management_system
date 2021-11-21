package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.User;

import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import com.qiaose.competitionmanagementsystem.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "菜单接口")
public class FrontendMenuController {

    @Autowired
    SysFrontendMenuTableService sysFrontendMenuTableService;

    @Autowired
    SysRoleFrontendMenuTableService sysRoleFrontendMenuTableService;

    @Autowired
    MyUtils myUtils;

    @GetMapping("/getMenuList")
    @ApiOperation(value = "返回角色初始菜单", notes = "需要用户传入请求头，从中获取个人信息")
    public R getCurMenu(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        System.out.println(token);
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        User user = userService.selectByAccountName(username);
        User user = myUtils.TokenGetUserByName(request);
        List<SysRoleFrontendMenuTable> sysRoleFrontendMenuTable = sysRoleFrontendMenuTableService.selectByRoleId(Long.valueOf(user.getRoleId()));
        List<SysFrontendMenuTable> sysFrontendDtos = new ArrayList<>();
        for (SysRoleFrontendMenuTable roleFrontendMenuTable : sysRoleFrontendMenuTable) {
            List<SysFrontendMenuTable> sysFrontendMenuTables = sysFrontendMenuTableService.listWithTree(roleFrontendMenuTable.getAuthorityId());
            for (SysFrontendMenuTable sysFrontendMenuTable : sysFrontendMenuTables) {
                sysFrontendDtos.add(sysFrontendMenuTable);
            }
        }

//        String s = JSONObject.toJSONString(sysFrontendDtos);
        return R.ok(sysFrontendDtos);
    }


    @GetMapping("/getPermCode")
    public R getPermCode(){
        return R.ok(1);
    }



    @PostMapping("/insert")
    @ApiOperation(value = "插入菜单信息", notes = "需要前端传入body和请求头")
    @Transactional
    public R InsertFrontMenu(@RequestBody SysFrontendDto sysFrontendDto,HttpServletRequest request){
        //获取角色id
//        String token = request.getHeader("Authorization");
//        System.out.println(token);
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        User user = userService.selectByAccountName(username);
        User user = myUtils.TokenGetUserByName(request);
        String roleId = user.getRoleId();
        //数据插入菜单表
        SysFrontendMenuTable sysFrontendMenuTable = sysFrontendMenuTableService.F_DtoToF_Po(sysFrontendDto);
        Long id = IDUtils.CreateId();
        System.out.println(id);
        sysFrontendMenuTable.setId(id);
        sysFrontendMenuTable.setCreatedBy(Long.valueOf(roleId));
        sysFrontendMenuTable.setCreateTime(DateKit.getNow());
        //完成一次菜单表插入
        int i = sysFrontendMenuTableService.insertSelective(sysFrontendMenuTable);

        //创建一个RoleFront表
        SysRoleFrontendMenuTable sysRoleFrontendMenuTable = new SysRoleFrontendMenuTable();
        sysRoleFrontendMenuTable.setRoleId(Long.valueOf(roleId));
//        sysRoleFrontendMenuTable.setRoleId(Long.valueOf(1));
        sysRoleFrontendMenuTable.setAuthorityId(id);
        sysRoleFrontendMenuTable.setId(IDUtils.CreateId());
        sysRoleFrontendMenuTable.setAuthorityType("MENU");
        sysRoleFrontendMenuTable.setCreateTime(DateKit.getNow());
        sysRoleFrontendMenuTable.setAuthorityId(Long.valueOf(roleId));
        //完成插入角色菜单表
        int i1 = sysRoleFrontendMenuTableService.insertSelective(sysRoleFrontendMenuTable);
        if ( i !=1 || i1 !=1){
            return R.failed("插入失败");
        }

        return R.ok("插入成功");
    }



    @GetMapping("/getMenuAllList")
    @ApiOperation(value = "返回角色初始菜单", notes = "需要用户传入请求头，从中获取个人信息")
    public R getAllMenu() {
        List<SysFrontendMenuTable> sysFrontendMenuTables = sysFrontendMenuTableService.selectByAll();
        List<SysFrontendMenuTable> sysFrontendDtos = new ArrayList<>();
        for (SysFrontendMenuTable frontendMenuTable : sysFrontendMenuTables) {
            //                                                                                                    getAuthorityId == front menu id
            List<SysFrontendMenuTable> sysFrontendMenu = sysFrontendMenuTableService.listWithTree(frontendMenuTable.getId());
            for (SysFrontendMenuTable sysFrontendMenuTable : sysFrontendMenu) {
                sysFrontendDtos.add(sysFrontendMenuTable);
            }
        }

//        String s = JSONObject.toJSONString(sysFrontendDtos);
        return R.ok(sysFrontendDtos);
    }


}




