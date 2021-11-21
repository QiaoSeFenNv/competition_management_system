package com.qiaose.competitionmanagementsystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.utils.TaleUtils;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/getMenuList")
    @ApiOperation(value = "返回角色初始菜单", notes = "需要用户传入请求头，从中获取个人信息")
    public R getCurMenu(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);
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

}
