package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.SysBackendApiTable;
import com.qiaose.competitionmanagementsystem.service.SysBackendApiTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleBackendApiTableService;
import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import com.qiaose.competitionmanagementsystem.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "后台权限接口")
@RequestMapping("/back")
public class BackendApiController {


    @Autowired
    SysBackendApiTableService sysBackendApiTableService;


    @Autowired
    SysRoleBackendApiTableService sysRoleBackendApiTableService;

    @Autowired
    MyUtils myUtils;





    @GetMapping("/getAllApi")
    @ApiOperation(value="返回所有的权限接口",notes="不需要前端携带任何数据")
    public R getBackAllApi(){
        List<SysBackendApiTable> sysBackendApiTables = sysBackendApiTableService.selectByAll();
        return R.ok(sysBackendApiTables);
    }

    @GetMapping("/getCurApi")
    @ApiOperation(value="返回当前角色的权限接口",notes="这个是在框架中体现，每一个用户进来，直接封锁")
    public R getCurBackApi(){
        return R.ok("冲冲冲，这也太简单了吧");
    }


    @PostMapping("/insert")
    @ApiOperation(value="添加api的权限接口",notes="需要携带body(不可以携带id值‍))(不需要携带得可以不用写进行body里面)")
    @Transactional(rollbackFor = Exception.class)
    public R InsertBackApi(@RequestBody SysBackendApiTable sysBackendApiTable){
        // 信息 以及主要信息不能为空
        if (sysBackendApiTable == null || sysBackendApiTable.getBackendApiUrl() == null || sysBackendApiTable.getBackendApiMethod() == null) {
            return R.failed("信息为空无法插入");
        }

        Long backApiId = IDUtils.CreateId();
        sysBackendApiTable.setBackendApiId(String.valueOf(backApiId));
        int i = sysBackendApiTableService.insertSelective(sysBackendApiTable);

        if (i <= 0){
            return R.failed("插入失败");
        }

        return R.ok("插入成功");
    }



    @PostMapping("/delete")
    @ApiOperation(value="删除api的权限接口",notes="需要携带body,body附带一个id(只需要一个id即可)")
    @Transactional(rollbackFor = Exception.class)
    public R DeleteBackApi(@RequestBody SysBackendApiTable sysBackendApiTable){
        if (sysBackendApiTable == null || sysBackendApiTable.getBackendApiId() == null){
            return R.failed("信息不全");
        }
        String backendApiId = sysBackendApiTable.getBackendApiId();
        int i = sysBackendApiTableService.deleteByPrimaryKey(backendApiId);
        //删除可能需要保留中间表一起删除  别删除可能后面要还原(因为我插入一条数据，它并没有绑定，这时候删除，对应得中间表无数据，无法删除成功)
//        int j = sysRoleBackendApiTableService.deleteByBackApiId(backendApiId);

        if (i <= 0){
            return R.failed("删除失败");
        }

        return R.ok("删除成功");
    }





    @PostMapping("/update")
    @ApiOperation(value="更新api的权限接口",notes="需要携带body,需要携带id值")
    @Transactional(rollbackFor = Exception.class)
    public R UpdateBackApi(@RequestBody SysBackendApiTable sysBackendApiTable){
        //更新不能修改id
        int i = sysBackendApiTableService.updateByPrimaryKeySelective(sysBackendApiTable);
        if (i != 1){
            return R.failed("更新失败");
        }

        return R.ok("更新成功");
    }







}
