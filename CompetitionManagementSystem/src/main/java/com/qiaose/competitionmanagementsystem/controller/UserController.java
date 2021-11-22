package com.qiaose.competitionmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.UserDto;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;


@Slf4j
@RestController
@Api(value = "用户接口")
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    SysRoleUserTableService sysRoleUserTableService;

    @Autowired
    SysRoleTableService sysRoleTableService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @GetMapping("/search")
    @ApiOperation(value="查询所有用户", notes="显示所有用户数据,封装未R.ok类型")
    public R getList() {
        List<User> list =  userService.list();
        return  R.ok(list);
    }



    @GetMapping("/getUserInfo")
    @ApiOperation(value="查询自己的用户信息", notes="由前端请求头中获取token,在利用token获得用户信息")
    public R getUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);
        UserDto userDto = userService.PoToDto(user);
        userDto.setToken(token);
        SysRoleUserTable sysRoleUserTable = sysRoleUserTableService.selectByRoleId(user.getRoleId());
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectByPrimaryKey(sysRoleUserTable.getRoleId());
        HashMap<String,String> role = new HashMap<>();
        for (SysRoleTable sysRoleTable : sysRoleTables) {
            role.put("roleName",sysRoleTable.getRoleName());
            role.put("value",sysRoleTable.getRoleId());
        }
        List list = new ArrayList(Collections.singleton(role));
        userDto.setRoles(list);
        return R.ok(userDto);
    }




    /**
     * 添加用户、用户自行注册。
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value="用户注册信息", notes="由前端发送用户信息,后端保存用户信息")
    public R register(User user) {
        try {
            System.out.println("register = " + user);
            if (userService.register(user)) {
                return  R.ok("成功注册");
            }
        }catch (Exception e){
            return R.failed(e.getMessage());
        }
        return R.failed("注册失败");
    }






}
