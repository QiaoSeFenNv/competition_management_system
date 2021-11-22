package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.SysBackendApiTable;
import com.qiaose.competitionmanagementsystem.service.SysBackendApiTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleBackendApiTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "后台权限接口")
@RequestMapping("/back")
public class BackendApiController {


    @Autowired
    SysBackendApiTableService sysBackendApiTableService;


    @Autowired
    SysRoleBackendApiTableService sysRoleBackendApiTableService;





    @GetMapping("/getAllApi")
    @ApiOperation(value="返回所有的权限接口",notes="")
    public R getAllApi(){

        List<SysBackendApiTable> sysBackendApiTables = sysBackendApiTableService.selectByAll();

        return R.ok(sysBackendApiTables);
    }


    

}
