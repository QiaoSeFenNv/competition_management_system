package com.qiaose.competitionmanagementsystem.controller;

import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("角色管理")
@RequestMapping("/role")
public class RoleController {



    @Autowired
    SysRoleTableService sysRoleTableService;

    @Autowired
    SysRoleUserTableService sysRoleUserTableService;


}
