package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;

import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.dto.CreditDto;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(value = "用户信息接口")
@RequestMapping("/userInfo")
@Validated
@Slf4j
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Autowired
    CollegeInfoService collegeInfoService;

    @Autowired
    SysRoleUserTableService sysRoleUserTableService;

    @Resource
    SysRoleTableService sysRoleTableService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;


    @GetMapping("/getCurStu")
    @ApiOperation(value = "查询学生信息", notes = "返回数据为学生所有信息")
//    @Transactional(rollbackFor = Exception.class)
    public R getCurStu(@RequestParam(required = false) String workId) {
        //根据学号查询学生信息对象
        UserInfo userInfo = userInfoService.selectByWorkId(workId);
        if (userInfo == null) {
            return R.ok("无用户信息");
        }

        return R.ok(userInfo);
    }

    //it may duplicate with getUserInfo method in UserController
    @GetMapping("/getCurrentUserInfo")
    @ApiOperation(value = "获取当前用户UserInfo", notes = "返回数据为用户所有信息")
    public R getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userId = jwtTokenUtil.getUsernameFromToken(token);
        UserInfo userInfo = userInfoService.selectByWorkId(userId);
        if (userInfo == null) {
            return R.ok("无用户信息");
        }
        return R.ok(userInfo);
    }

    @GetMapping("/getStuInfo")
    @ApiOperation(value = "根据条件查询", notes = "返回数据为学生所有信息")
//    @Transactional(rollbackFor = Exception.class)
    public R getStuInfo(@RequestParam String userSelect) {

        if (StringUtils.isNotBlank(userSelect)) {
            List<UserInfo> userInfoList = userInfoService.selectByUserSelect(userSelect);
            return R.ok(userInfoList);
        }
        return R.ok("传递参数为空");
    }

    @PostMapping("/insertCurStu")
    @ApiOperation(value = "插入当前学生信息", notes = "传入对象")
    @Transactional(rollbackFor = Exception.class)
    public R insertCurStu(@RequestBody UserInfo userInfo) {

        if (userInfo.getUserId() == null) {
            return R.failed("无学工号、无法生成");
        }

        //从对象中取除 银行和班级对象，然后插入对应表中
        insertOrgUser(userInfo);
        //插入整个对象
        userInfo.setCreateTime(DateKit.getNow());

        int k = userInfoService.insertSelective(userInfo);

        if (k <= 0) {
            return R.failed("插入失败");
        }
        return R.ok("插入成功");
    }

    public void insertOrgUser(UserInfo userInfo) {
        User user = new User();
        //名称
        user.setUserId(userInfo.getUserId());
        user.setUserPassword(bCryptPasswordEncoderUtil.encode("000000"));
        int j = userService.insertSelective(user);
        //绑定角色
        SysRoleUserTable sysRoleUserTable = new SysRoleUserTable();
        if (userInfo.getRole() == null) {
            //默认为学生
            sysRoleUserTable.setRoleId("2");
            sysRoleUserTable.setUserId(userInfo.getUserId());
            sysRoleUserTableService.insertSelective(sysRoleUserTable);
        } else {
            for (String s : userInfo.getRole()) {
                SysRoleTable sysRoleTable = sysRoleTableService.selectByName(s);
                if (sysRoleTable != null) {
                    sysRoleUserTable.setRoleId(sysRoleTable.getRoleId());
                    sysRoleUserTable.setUserId(userInfo.getUserId());
                    sysRoleUserTableService.insertSelective(sysRoleUserTable);
                    return;
                }
                sysRoleUserTable.setRoleId("2");
                sysRoleUserTable.setUserId(userInfo.getUserId());
                sysRoleUserTableService.insertSelective(sysRoleUserTable);
            }
        }
    }

    @PostMapping("/insertAll")
    @ApiOperation(value = "批量插入", notes = "")
    @Transactional(rollbackFor = Exception.class)
    public R insertAll(@RequestBody List<UserInfo> userList) {

        for (UserInfo userInfo : userList) {
            if (userInfoService.selectByWorkId(userInfo.getUserId()) != null) {
                break;
            }
            //设置部门id然后插入数据
            Integer id = collegeInfoService.selectByName(userInfo.getDeptId());

            log.info("部门对应id：{}", id);

            userInfo.setDeptId(String.valueOf(id));
            //插入数据
            userInfoService.insertSelective(userInfo);
            //插入到user表以及绑定角色
            insertOrgUser(userInfo);
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

    @PostMapping("/deleteCurStu")
    @ApiOperation(value = "删除当前学生信息", notes = "携带id")
    @Transactional(rollbackFor = Exception.class)
    public R deleteCurStu(@RequestBody UserInfo userInfo) {

        UserInfo userInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());

        int i = userInfoService.deleteByPrimaryKey(userInfo1.getId());
        int k = userService.deleteByUserId(userInfo1.getUserId());
        int i1 = sysRoleUserTableService.deleteByUserId(userInfo1.getUserId());

        return R.ok("删除成功");
    }


    @GetMapping("/getCreditInfo")
    @ApiOperation(value = "查询学分", notes = "返回数据为学生所有信息")
//    @Transactional(rollbackFor = Exception.class)
    public R getCreditInfo(@RequestParam(defaultValue = "1", value = "page") Integer page
            , @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
            , @RequestParam(required = false, defaultValue = "") String userId) {
        PageHelper.startPage(page, pageSize);
        List<UserInfo> userInfoList = userInfoService.selectByUserCredit(userId);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
        List<UserInfo> list1 = pageInfo.getList();
        PageDto pageDto = new PageDto();
        pageDto.setItems(list1);
        pageDto.setTotal((int) pageInfo.getTotal());
        return R.ok(pageDto);
    }

    @PostMapping("/updateCreditInfo")
    @ApiOperation(value = "修改学分", notes = "返回数据为学生所有信息")
//    @Transactional(rollbackFor = Exception.class)
    public R getCreditInfo(@RequestBody CreditDto creditDto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(creditDto, userInfo);
        int i = userInfoService.updateByUserSelective(userInfo);
        return R.ok("成功");
    }

}
