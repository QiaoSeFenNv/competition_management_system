package com.qiaose.competitionmanagementsystem.controller;



import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.UserDto;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;

import com.qiaose.competitionmanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

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
        UserDto userDto = UserDto.builder()
                .avatar(user.getUserAvatarurl())
                .token(token)
                .userId(user.getUserId())
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




    @PostMapping("/changePas")
    @ApiOperation(value="修改密码,在已登录的情况下", notes="二个信息，可以放在body中还要携带一个请求头")
    @Transactional(rollbackFor = {Exception.class})
    public R changePass(HttpServletRequest request,
                        @RequestParam(required = true) String OriginPas,
                        @RequestParam(required = true) String passWord){

        String token = request.getHeader("Authorization");
        User user = userService.selectByUserId(jwtTokenUtil.getUsernameFromToken(token));
        //原始密码比较
        if (!bCryptPasswordEncoderUtil.matches(OriginPas,user.getUserPassword())) {
            return R.failed("原密码错误");
        }
        user.setUserPassword( bCryptPasswordEncoderUtil.encode(passWord));
        userService.updateByPrimaryKeySelective(user);

        return R.ok("修改成功");
    }




//    @PostMapping("/NoChangePas")
//    @ApiOperation(value="修改密码,在未登录的情况下", notes="5个信息，名称保持一致")
//    @Transactional(rollbackFor = {Exception.class})
//    public R changePas(@RequestParam(required = true) String UserId,
//                       @RequestParam(required = true) String OriginPas,
//                       @RequestParam(required = true) String NewPas,
//                       @RequestParam(required = true) String Verification){
//
//
//        User user = userService.selectByUserId(UserId);
//        if (user == null){
//            return R.failed("账号错误");
//        }
//        //通过详细的用户表查询
////        String mailTo = user.getEmail();
//        if (mailTo == null){
//            return R.failed("未绑定邮箱,无法修改密码");
//        }
//        String s = stringRedisTemplate.opsForValue().get(mailTo);
//        if ( !Verification.equals(s)){
//            return R.failed("验证码输入错误");
//        }
//
//        String password = user.getPassword();
//        boolean matches = bCryptPasswordEncoderUtil.matches(OriginPas, password);
//
//        if (matches == false){
//            return R.failed("密码错误,请重新输入");
//        }
//        String encode = bCryptPasswordEncoderUtil.encode(NewPas);
//        user.setPassword(encode);
//        int i = userService.updateByPrimaryKeySelective(user);
//
//
//        return R.ok("");
//    }

}
