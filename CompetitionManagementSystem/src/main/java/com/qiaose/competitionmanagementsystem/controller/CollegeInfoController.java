package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;

import com.qiaose.competitionmanagementsystem.entity.dto.SysUserDto;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(value = "二级学院接口")
@RequestMapping("/college")
public class CollegeInfoController {

    @Autowired
    CollegeInfoService collegeInfoService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;




    @GetMapping("/getAllDeptInfo")
    @ApiOperation(value="查询所有部门", notes="显示所有部门")
    public R getAllDeptInfo(@RequestParam(required = false ,value = "collegeName") String collegeName ) {

        CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(1);
        List<CollegeInfo> collegeInfoList = listWithTree(collegeInfo.getId());
        return  R.ok(collegeInfoList);

    }


    /**
     * 查询二级学院对应的学生信息
     * @param page
     * @param pageSize
     * @param deptId
     * @return
     */
    @GetMapping("/getCurStuInfo")
    @ApiOperation(value="查询部门对应的学生信息", notes="携带三个参数")
    public R getCurStuInfo(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
            ,@RequestParam(required = false,value = "deptId") Integer deptId) {

        //根据deptId在部门表中查询祖先包含id对应的集合
        //在通过集合中的每一个id值取查询user_info中的deptId
        List<UserInfo> listUser = new ArrayList<>();

        List<CollegeInfo> collegeInfoList = collegeInfoService.selectByAncestors(deptId+"");

        listUser.addAll(userInfoService.selectByDeptId(deptId+""));

        for (CollegeInfo collegeInfo : collegeInfoList) {
            listUser.addAll(userInfoService.selectByDeptId(collegeInfo.getId()+""));
        }
        //去重
        List<UserInfo> distinct = listUser.stream().distinct().collect(Collectors.toList());

        //分页
        PageHelper.startPage(page,pageSize);
        List<User> userList = new ArrayList<>();
        for (UserInfo userInfo : distinct) {
            User user = userService.selectByUserId(userInfo.getUserId());
            user.setUsername(userInfo.getUserName());
            user.setUserStatus(userInfo.getUserStatus());
            userList.add(user);
        }

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        List<User> list = pageInfo.getList();
        PageDto pageDto = new PageDto();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);
    }



    @PostMapping("/insertDeptInfo")
    @ApiOperation(value="插入部门", notes="前端携带body,需要携带父类的祖先，祖先类型为字符串")
    public R insertDeptInfo(@RequestBody CollegeInfo collegeInfo) {

        int id = Math.toIntExact(collegeInfo.getParentId());
        if (id<=0) {
            return R.failed("请联系开发人员,插入失败");
        }
        CollegeInfo collegeInfo1 = collegeInfoService.selectByPrimaryKey(id);

        String ancestors = collegeInfo1.getAncestors();

        if (ancestors == null){
            ancestors =""+collegeInfo.getParentId();
        }else{
            ancestors +=","+collegeInfo.getParentId();
        }

        collegeInfo.setAncestors(ancestors);

        int i = collegeInfoService.insertSelective(collegeInfo);

        if ( i <= 0){
            return R.failed("插入失败");
        }

        return  R.ok("插入成功");
    }



    @PostMapping("/updateDeptInfo")
    @ApiOperation(value="更新部门", notes="请求body里面要携带id。应该只让修改名称吧")
    public R updateDeptInfo(@RequestBody CollegeInfo collegeInfo) {

        int i = collegeInfoService.updateByPrimaryKeySelective(collegeInfo);
        if ( i <= 0){
            return R.failed("更新失败");
        }
        return  R.ok("更新成功");
    }

    @PostMapping("/deleteDeptInfo")
    @ApiOperation(value="删除部门", notes="请求body只携带id,还要携带祖先哦，我会把id下的所有子节点都删除")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteDeptInfo(@RequestBody CollegeInfo collegeInfo) {

        CollegeInfo deleteObj = collegeInfoService.selectByPrimaryKey(collegeInfo.getId());

        int i = collegeInfoService.deleteByPrimaryKey(collegeInfo.getId());

        int j = collegeInfoService.deleteByParentId(Long.valueOf(deleteObj.getId()));

        if ( i <= 0 && j<=0){
            return R.failed("删除失败");
        }
        return  R.ok("删除成功");
    }





    public List<CollegeInfo> listWithTree(Integer id){
        //获得父节点对象  1
        CollegeInfo sysFrontendMenuTable = collegeInfoService.selectByPrimaryKey(id);

        //在去拿子类
        List<CollegeInfo> children = getChildren(sysFrontendMenuTable);
        sysFrontendMenuTable.setChildren(children);

        return Collections.singletonList(sysFrontendMenuTable);
    }


    public List<CollegeInfo> getChildren(CollegeInfo sysFrontendMenuTable){

        CollegeInfo baba =  sysFrontendMenuTable;

        // 2 3
        List<CollegeInfo> children = collegeInfoService.selectByParentId(Long.valueOf(baba.getId()));
        for (CollegeInfo child : children) {
            List<CollegeInfo> children1 = getChildren(child);
            child.setChildren(children1);
        }
        return children;
    }


}
