package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;

import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.entity.BankTable;

import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.service.BankTableService;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;

import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "用户信息接口")
@RequestMapping("/userInfo")
@Validated
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Autowired
    CollegeInfoService collegeInfoService;

    @Resource
    BankTableService bankTableService;


    @Autowired
    SysRoleUserTableService sysRoleUserTableService;

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;





    @GetMapping("/getCurStu")
    @ApiOperation(value = "查询当前学生信息", notes = "返回数据为学生所有信息")
//    @Transactional(rollbackFor = Exception.class)
    public R getCurStu(@RequestParam(required = false) String workId) {
        //根据学号查询学生信息对象
        UserInfo userInfo = userInfoService.selectByWorkId(workId);
        if (userInfo == null) {
            return R.ok("无用户信息");
        }


        return R.ok(userInfo);
    }

    @PostMapping("/insertCurStu")
    @ApiOperation(value = "插入当前学生信息", notes = "传入对象")
    @Transactional(rollbackFor = Exception.class)
    public R insertCurStu(@RequestBody  UserInfo userInfo) {

        if (userInfo.getUserId()==null) {
            return R.failed("无学工号、无法生成");
        }

        //从对象中取除 银行和班级对象，然后插入对应表中
        User user = new User();
        //名称
        user.setUserId(userInfo.getUserId());
        user.setUserPassword(bCryptPasswordEncoderUtil.encode("000000"));
        int j = userService.insertSelective(user);
        //绑定角色
        SysRoleUserTable sysRoleUserTable = new SysRoleUserTable();
        sysRoleUserTable.setRoleId(userInfo.getRole()+"");
        sysRoleUserTable.setUserId(userInfo.getUserId());
        sysRoleUserTableService.insertSelective(sysRoleUserTable);
        //插入整个对象
        userInfo.setCreateTime(DateKit.getNow());


        int k = userInfoService.insertSelective(userInfo);

        if ( k <= 0) {
            return R.failed("插入失败");
        }
        return R.ok("插入成功");
    }

    @PostMapping("/updateCurStu")
    @ApiOperation(value = "更新当前学生信息", notes = "携带参数需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R updateCurStu(@RequestBody UserInfo userInfo) {

        UserInfo userInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());
        User user = userService.selectByUserId(userInfo1.getUserId());
        List<SysRoleUserTable> sysRoleUserTables = sysRoleUserTableService.selectByUserId(userInfo1.getUserId());
        //更新的内容
        user.setUserId(userInfo.getUserId());
        for (SysRoleUserTable sysRoleUserTable : sysRoleUserTables) {
            sysRoleUserTable.setUserId(userInfo.getUserId());
            String[] role = userInfo.getRole();
            for (String s : role) {
                sysRoleUserTable.setRoleId(s);
                int i = sysRoleUserTableService.updateByPrimaryKeySelective(sysRoleUserTable);
            }
        }
        int k = userInfoService.updateByPrimaryKeySelective(userInfo);
        int i = userService.updateByPrimaryKeySelective(user);
        return R.ok("更新成功");
    }

    @PostMapping  ("/deleteCurStu")
    @ApiOperation(value = "删除当前学生信息", notes = "携带id")
    @Transactional(rollbackFor = Exception.class)
    public R deleteCurStu(@RequestBody UserInfo userInfo) {

        UserInfo userInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());

        int i = userInfoService.deleteByPrimaryKey(userInfo1.getId());
        int k = userService.deleteByUserId(userInfo1.getUserId());
        int i1 = sysRoleUserTableService.deleteByUserId(userInfo1.getUserId());

        return R.ok("删除成功");
    }

}
