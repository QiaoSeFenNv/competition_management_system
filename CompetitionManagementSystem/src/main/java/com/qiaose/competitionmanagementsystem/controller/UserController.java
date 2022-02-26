package com.qiaose.competitionmanagementsystem.controller;



import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.SysChangePass;
import com.qiaose.competitionmanagementsystem.entity.dto.UserDto;
import com.qiaose.competitionmanagementsystem.exception.TipException;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;

import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

import java.util.*;


@Slf4j
@RestController
@Api(value = "用户接口")
@RequestMapping("/user")
@Validated
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    SysRoleUserTableService sysRoleUserTableService;

    @Autowired
    SysRoleTableService sysRoleTableService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @GetMapping("/search")
    @ApiOperation(value="查询所有用户", notes="显示所有用户数据,封装未R.ok类型")
    public R getList(@RequestParam(defaultValue = "1", value = "page") Integer page
    ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<User> list = userService.getAllUser();
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


        //分页操作
        PageInfo<User> pageInfo = new PageInfo<>();
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

        User user = userService.selectByUserId(username);
        UserInfo userInfo = userInfoService.selectByWorkId(username);
        UserDto userDto = UserDto.builder()
                .avatar(user.getUserAvatarurl())
                .token(token)
                .userId(user.getUserId())
                .userName(userInfo.getUserName())
                .email(userInfo.getEmail())
                .phone(userInfo.getTelephone())
                .build();
        userDto.setToken(token);
        List<SysRoleUserTable> sysRoleUserTable = sysRoleUserTableService.selectByUserId(user.getUserId());
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


    @GetMapping("/updateUserInfo")
    @ApiOperation(value="查询自己的用户信息", notes="由前端请求头中获取token,在利用token获得用户信息")
    public R updateUserInfo(HttpServletRequest request,@RequestParam String phone,@RequestParam String email){


        String token = request.getHeader("Authorization");

        System.out.println(token);
        //
        String username = jwtTokenUtil.getUsernameFromToken(token);

        UserInfo userInfo = userInfoService.selectByWorkId(username);
        userInfo.setEmail(email);
        userInfo.setTelephone(phone);

        int i = userInfoService.updateByPrimaryKeySelective(userInfo);

        return R.ok("更新成功");
    }


    @PostMapping("/changePas")
    @ApiOperation(value="修改密码,在已登录的情况下", notes="二个信息，可以放在body中还要携带一个请求头")
    @Transactional(rollbackFor = {Exception.class})
    public R changePass(HttpServletRequest request, @RequestBody SysChangePass sysChangePass){

        String token = request.getHeader("Authorization");
        User user = userService.selectByUserId(jwtTokenUtil.getUsernameFromToken(token));
        //原始密码比较
        if (!bCryptPasswordEncoderUtil.matches(sysChangePass.getOriginPas(), user.getUserPassword())) {
            return R.failed("原密码错误");
        }
        user.setUserPassword( bCryptPasswordEncoderUtil.encode(sysChangePass.getNewPas()));
        userService.updateByPrimaryKeySelective(user);

        return R.ok("修改成功");
    }





    @PostMapping("/NoLoginPas")
    @ApiOperation(value="修改密码,在未登录的情况下", notes="5个信息，名称保持一致")
    @Transactional(rollbackFor = {Exception.class})
    public R changePas(@RequestBody SysChangePass sysChangePass){

        UserInfo userInfo = userInfoService.selectByEmail(sysChangePass.getEmail());

        User user = userService.selectByUserId(userInfo.getUserId());

        if (sysChangePass.getEmail() == null){
            return R.failed("未绑定邮箱,无法修改密码");
        }
        String s = stringRedisTemplate.opsForValue().get(sysChangePass.getEmail());
        if ( !sysChangePass.getVerification().equals(s)){
            return R.failed("验证码输入错误");
        }


        String encode = bCryptPasswordEncoderUtil.encode(sysChangePass.getNewPas());
        user.setUserPassword(encode);
        int i = userService.updateByPrimaryKeySelective(user);

        stringRedisTemplate.delete(sysChangePass.getEmail());
        
        return R.ok("");
    }



}
