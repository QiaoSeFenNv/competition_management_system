package com.qiaose.competitionmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "用户接口")
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/search")
    @ApiOperation(value="查询所有用户", notes="显示所有用户数据,封装未R.ok类型")
    public R getList() {
        List<User> list =  userService.list();
        return  R.ok(list);
    }

    /**
     * 添加用户、用户自行注册。
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value="注册功能", notes="用户注册功能")
    public R register(@RequestBody(required = false) User user) {
        try {
            System.out.println("registerVo = " + user);
            return  R.ok(userService.register(user));
        }catch (Exception e){
            return R.failed(e.getMessage());
        }
    }





}
