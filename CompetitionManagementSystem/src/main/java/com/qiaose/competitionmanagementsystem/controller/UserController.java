package com.qiaose.competitionmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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





}
