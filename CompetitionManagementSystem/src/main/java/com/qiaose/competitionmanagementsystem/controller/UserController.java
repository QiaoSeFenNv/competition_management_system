package com.qiaose.competitionmanagementsystem.controller;


import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "用户信息管理")
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register/{id}")
    @ApiOperation(value="登录", notes="根据省市区的pid获取地址信息")
    public User getUser(@PathVariable("id") Integer id){
        return userService.selectByPrimaryKey(id);
    }

    @GetMapping("/hello")
    @ApiOperation(value="获取省市区信息", notes="根据省市区的pid获取地址信息")
    public String getUser(){
        return "hello";
    }




}
