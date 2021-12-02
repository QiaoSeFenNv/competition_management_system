package com.qiaose.competitionmanagementsystem.controller;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.UserDto;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;

import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;

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

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @GetMapping("/search")
    @ApiOperation(value="查询所有用户", notes="显示所有用户数据,封装未R.ok类型")
    public R getList(@RequestParam(defaultValue = "1", value = "page") Integer page
    ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<User> list =  userService.list();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        List<User> list1 = pageInfo.getList();

        PageDto pageDto = new PageDto();
        pageDto.setItems(list1);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);
    }


    @GetMapping("/findUser")
    @ApiOperation(value="模糊查询用户", notes="查找带有条件用户数据")
    public R getList(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "phone",required = false) String phone,
            @RequestParam(value = "role",required = false) String role,
            @RequestParam(defaultValue = "1", value = "page") Integer page,
            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {

        SysRoleTable sysRoleTable = null;
        //通过角色表来获得id号，再将id号进行查询  id值可能会很多
        if (StringUtil.isNullOrEmpty(role)){
            sysRoleTable = sysRoleTableService.selectByName(role);
        }
        //分页操作
        PageHelper.startPage(page,pageSize);
        //将三参数封装为一个对象 动态查询
        User user = new User();
        user.setAccountName(name);
        user.setTelephone(Convert.toStr(phone));
        if (sysRoleTable != null)
            user.setRoleId(sysRoleTable.getRoleId());
        List<User> list =  userService.findUser(user);
        //分页操作
        PageInfo<User> pageInfo = new PageInfo<>(list);
        List<User> list1 = pageInfo.getList();

        PageDto pageDto = new PageDto();
        pageDto.setItems(list1);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);
    }



    @GetMapping("/getUserInfo")
    @ApiOperation(value="查询自己的用户信息", notes="由前端请求头中获取token,在利用token获得用户信息")
    public R getUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        System.out.println(token);
        //
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);
        UserDto userDto = userService.PoToDto(user);
        userDto.setToken(token);
        List<SysRoleUserTable> sysRoleUserTable = sysRoleUserTableService.selectByRoleId(user.getRoleId());
        List<SysRoleTable> sysRoleTables = new ArrayList<>();
        for (SysRoleUserTable roleUserTable : sysRoleUserTable) {
            List<SysRoleTable> sysRoleTables1 = sysRoleTableService.selectByPrimaryKey(roleUserTable.getRoleId());
            for (SysRoleTable sysRoleTable : sysRoleTables1) {
                sysRoleTables.add(sysRoleTable);
            }
        }
        HashMap<String,String> role = new HashMap<>();
        for (SysRoleTable sysRoleTable : sysRoleTables) {
            role.put("roleName",sysRoleTable.getRoleName());
            role.put("value",sysRoleTable.getRoleId());
        }
        List list = new ArrayList<>(Collections.singleton(role));
        userDto.setRoles(list);
        return R.ok(userDto);
    }




    @PostMapping("/change")
    @ApiOperation(value="修改密码,在已登录的情况下", notes="三个信息，可以放在body中")
    @Transactional(rollbackFor = {Exception.class})
    public R changePass(@RequestParam(required = true) String username,
                        @RequestParam(required = true) String OncePassword,
                        @RequestParam(required = true) String passWord){

        User user = userService.selectByAccountName(username);
        if (user == null)
            return R.failed("账号输入错误");
        if (!OncePassword.equals(passWord)) {
            return R.failed("两次密码不同");
        }

        String replacePs = bCryptPasswordEncoderUtil.encode(passWord);

        user.setPassword(replacePs);

        int i = userService.updateByPrimaryKeySelective(user);

        if (i<=0){
            return R.failed("");
        }

        return R.ok("修改成功");
    }






}
