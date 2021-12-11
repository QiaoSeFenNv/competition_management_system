package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;

import com.qiaose.competitionmanagementsystem.entity.dto.SysUserDto;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Api(value = "二级学院接口")
@RequestMapping("/college")
public class CollegeInfoController {

    @Autowired
    CollegeInfoService collegeInfoService;

//    @Autowired
//    UserInfoController userInfoController;




    @GetMapping("/getAllDeptInfo")
    @ApiOperation(value="查询所有部门", notes="显示所有部门")
    public R getAllDeptInfo(@RequestParam(required = false ,value = "collegeName") String collegeName ) {

        CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(1);
        List<CollegeInfo> collegeInfoList = listWithTree(collegeInfo.getId());
        return  R.ok(collegeInfoList);

    }


//    /**
//     * 查询二级学院对应的学生信息
//     * @param page
//     * @param pageSize
//     * @param deptId
//     * @return
//     */
//    @GetMapping("/getCurStuInfo")
//    @ApiOperation(value="查询二级学院对应的学生信息", notes="携带三个参数")
//    public R getCurStuInfo(@RequestParam(defaultValue = "1", value = "page") Integer page
//            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
//            ,@RequestParam(required = false,value = "deptId") Integer deptId) {
//
//
//        List<SysUserDto> listUser = new ArrayList<>();
//        PageHelper.startPage(page,pageSize);
//        List<StudentInfo> studentInfo = null;
//        if (deptId == null){
//            studentInfo = studentInfoService.selectByAll();
//            //将student封装成为SysUser
//            for (StudentInfo info : studentInfo) {
//                CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(info.getDeptId());
//                SysUserDto sysUserDto = StuChangeUser(info);
//                sysUserDto.setDepartment(collegeInfo.getCollegeName());
//                listUser.add(sysUserDto);
//            }
//        }else{
//            studentInfo = studentInfoService.selectByDeptId(deptId);
//            CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(deptId);
//            //将student封装成为SysUser
//            for (StudentInfo info : studentInfo) {
//                SysUserDto sysUserDto = StuChangeUser(info);
//                sysUserDto.setDepartment(collegeInfo.getCollegeName());
//                listUser.add(sysUserDto);
//            }
//        }
//
//        PageInfo<SysUserDto> pageInfo = new PageInfo<>(listUser);
//
//        List<SysUserDto> list = pageInfo.getList();
//
//        PageDto pageDto = new PageDto();
//        pageDto.setItems(list);
//        pageDto.setTotal((int) pageInfo.getTotal());
//        return  R.ok(pageDto);
//    }
//
//
//
//


    /*
     * 插入 名称和父id
     * 删除 只给id
     *
     * */


    @PostMapping("/insertDeptInfo")
    @ApiOperation(value="插入二级学院", notes="前端携带body,需要携带父类的祖先，祖先类型为字符串")
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
    @ApiOperation(value="更新二级学院", notes="请求body里面要携带id。应该只让修改名称吧")
    public R updateDeptInfo(@RequestBody CollegeInfo collegeInfo) {

        int i = collegeInfoService.updateByPrimaryKeySelective(collegeInfo);
        if ( i <= 0){
            return R.failed("更新失败");
        }
        return  R.ok("更新成功");
    }

    @PostMapping("/deleteDeptInfo")
    @ApiOperation(value="删除二级学院", notes="请求body只携带id,还要携带祖先哦，我会把id下的所有子节点都删除")
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
