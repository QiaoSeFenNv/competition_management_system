package com.qiaose.competitionmanagementsystem.controller.adminController;


import com.baomidou.mybatisplus.extension.api.R;

import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendButtonTable;

import com.qiaose.competitionmanagementsystem.service.adminImpl.SysFrontendButtonTableService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Api(value = "前端按钮权限接口")
@RequestMapping("/button")
public class FrontendButtonController {

    @Autowired
    SysFrontendButtonTableService sysFrontendButtonTableService;


    @GetMapping("/getButton")
    @ApiOperation(value = "查询按钮权限信息", notes = "需要前端传入body和请求头,如果name为空则全查,不为空模糊查询")
    @Transactional(rollbackFor = MethodArgumentNotValidException.class)
    public R getButton(@RequestParam(required = false)  String name , HttpServletRequest request) {
        if (StringUtils.isEmpty(name)) {
            List<SysFrontendButtonTable> list= sysFrontendButtonTableService.selectAll();
            return R.ok(list);
        }else{
            List<SysFrontendButtonTable> list= sysFrontendButtonTableService.selectByName(name);
            return R.ok(list);
        }

    }


    @PostMapping("/insert")
    @ApiOperation(value = "插入按钮权限信息", notes = "需要前端传入body和请求头")
    @Transactional(rollbackFor = MethodArgumentNotValidException.class)
    public R InsertFrontButton(@RequestBody @Valid SysFrontendButtonTable SysFrontendButtonTable, HttpServletRequest request) {

        int i = sysFrontendButtonTableService.insertSelective(SysFrontendButtonTable);
        if (i<=0) {
            return R.failed("");
        }
        return R.ok("");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新按钮权限信息", notes = "需要前端传入body和请求头")
    @Transactional(rollbackFor = MethodArgumentNotValidException.class)
    public R updateFrontButton(@RequestBody @Valid SysFrontendButtonTable SysFrontendButtonTable, HttpServletRequest request) {

        int i = sysFrontendButtonTableService.updateByPrimaryKeySelective(SysFrontendButtonTable);
        if (i<=0) {
            return R.failed("");
        }
        return R.ok("");
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除按钮权限信息", notes = "需要前端传入body和请求头")
    @Transactional(rollbackFor = MethodArgumentNotValidException.class)
    public R updateFrontButton(@RequestParam @NotNull Long id , HttpServletRequest request) {

        int i = sysFrontendButtonTableService.deleteByPrimaryKey(id);
        if (i<=0) {
            return R.failed("");
        }
        return R.ok("");
    }

}
