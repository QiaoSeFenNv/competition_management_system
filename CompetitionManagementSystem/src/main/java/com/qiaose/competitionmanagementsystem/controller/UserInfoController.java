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
        //用bankid查询银行西信息
        BankTable bankTable = bankTableService.selectByPrimaryKey(userInfo.getBankId());
        userInfo.setBankTable(bankTable);


        return R.ok(userInfo);
    }

    @PostMapping("/insertCurStu")
    @ApiOperation(value = "插入当前学生信息", notes = "传入对象")
    @Transactional(rollbackFor = Exception.class)
    public R insertCurStu(@RequestBody  UserInfo userInfo) {
        //从对象中取除 银行和班级对象，然后插入对应表中

        BankTable bankTable = userInfo.getBankTable();
        if (bankTable == null) {
            return R.failed("未携带银行体");
        }
        Integer bankId = IDUtils.getUUIDInOrderId();
        bankTable.setId(bankId);


        int i = bankTableService.insert(bankTable);

        User user = new User();
        //名称
        user.setUserId(userInfo.getUserId());
        user.setUserPassword(bCryptPasswordEncoderUtil.encode("000000"));
        int j = userService.insertSelective(user);

        //绑定角色
        SysRoleUserTable sysRoleUserTable = new SysRoleUserTable();
        sysRoleUserTable.setRoleId(1+"");
        sysRoleUserTable.setUserId(userInfo.getUserId());
        sysRoleUserTableService.insertSelective(sysRoleUserTable);


        //插入整个对象
        userInfo.setBankId(bankId);
        userInfo.setCreateTime(DateKit.getNow());

        int k = userInfoService.insertSelective(userInfo);

        if (i <= 0  || k <= 0) {
            return R.failed("插入失败");
        }
        return R.ok("插入成功");
    }

    @PostMapping("/updateCurStu")
    @ApiOperation(value = "更新当前学生信息", notes = "携带参数需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R updateCurStu(@RequestBody UserInfo userInfo) {

        //取银行id
        Integer bankId = userInfoService.selectByPrimaryKey(userInfo.getId()).getBankId();

        //根据银行id和传递来上的银行表进行更新
        BankTable bankTable = userInfo.getBankTable();
        bankTable.setId(bankId);
        bankTableService.updateByPrimaryKeySelective(bankTable);

        User user = new User();

        user.setUserId(userInfo.getUserId());
        //更新的内容
        int k = userInfoService.updateByPrimaryKeySelective(userInfo);

        return R.ok("更新成功");
    }

    @PostMapping("/deleteCurStu")
    @ApiOperation(value = "删除当前学生信息", notes = "携带id")
    @Transactional(rollbackFor = Exception.class)
    public R deleteCurStu(@RequestBody UserInfo userInfo) {

        UserInfo userInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());



        int i = userInfoService.deleteByPrimaryKey(userInfo.getId());
        int j = bankTableService.deleteByPrimaryKey(userInfo1.getBankId());
        int k = userService.deleteByUserId(userInfo1.getUserId());



        return R.ok("删除成功");
    }

}
