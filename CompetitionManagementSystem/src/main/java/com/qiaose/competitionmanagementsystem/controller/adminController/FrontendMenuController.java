package com.qiaose.competitionmanagementsystem.controller.adminController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendButtonTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;

import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import com.qiaose.competitionmanagementsystem.exception.TipException;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysFrontendButtonTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import com.qiaose.competitionmanagementsystem.utils.MyUtils;
import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(value = "前台菜单接口")
@RequestMapping("/front")
public class FrontendMenuController {

    @Autowired
    SysFrontendMenuTableService sysFrontendMenuTableService;

    @Autowired
    SysRoleFrontendMenuTableService sysRoleFrontendMenuTableService;

    @Autowired
    SysRoleUserTableService sysRoleUserTableService;

    @Autowired
    SysFrontendButtonTableService sysFrontendButtonTableService;
    
    @Autowired
    UserService userService;

    @Autowired
    MyUtils myUtils;

    @GetMapping("/getMenuList")
    @ApiOperation(value = "返回角色初始菜单", notes = "需要用户传入请求头，从中获取个人信息")
    public R getCurMenu(HttpServletRequest request,
                        @RequestParam(defaultValue = "1", value = "page") Integer page,
                        @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {

        User user = myUtils.TokenGetUserByName(request);

        //实际是角色id
        List<SysRoleUserTable> sysRoleUserTables = sysRoleUserTableService.selectByUserId(user.getUserId());

        if (Collections.isEmpty(sysRoleUserTables)) {
            throw new TipException("服务器过载，请勿过度重复刷新");
        }

        List<SysRoleFrontendMenuTable> sysRoleFrontendMenuTable = new ArrayList<>();
        //1 2
        for (SysRoleUserTable sysRoleUserTable : sysRoleUserTables) {
            List<SysRoleFrontendMenuTable> sysRoleFrontendMenuTables = sysRoleFrontendMenuTableService.selectByRoleId(sysRoleUserTable.getRoleId());
            for (SysRoleFrontendMenuTable roleFrontendMenuTable : sysRoleFrontendMenuTables) {
                sysRoleFrontendMenuTable.add(roleFrontendMenuTable);
            }
        }

        PageHelper.startPage(page,pageSize);

        List<SysFrontendMenuTable> sysFrontendDtos = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        List<SysFrontendButtonTable> ButtonList = new ArrayList<>();
        for (SysRoleFrontendMenuTable roleFrontendMenuTable : sysRoleFrontendMenuTable) {
            if (roleFrontendMenuTable.getAuthorityType().equals("MENU")) {
                SysFrontendMenuTable sysFrontendMenuTable = sysFrontendMenuTableService.selectByPrimaryKey(roleFrontendMenuTable.getAuthorityId());
                if ( !idList.contains(sysFrontendMenuTable.getId()) && !idList.contains(sysFrontendMenuTable.getParentId())){
                    idList.add(sysFrontendMenuTable.getId());
                }
            }
            if (roleFrontendMenuTable.getAuthorityType().equals("PERM")) {
                ButtonList.add( sysFrontendButtonTableService.selectByPrimaryKey(roleFrontendMenuTable.getAuthorityId()) );
            }
        }
        idList.forEach(System.out::println);
        for (Long aLong : idList) {
            List<SysFrontendMenuTable> sysFrontendMenuTables = sysFrontendMenuTableService.listWithTree(aLong,sysRoleFrontendMenuTable);
            sysFrontendDtos.addAll(sysFrontendMenuTables);
        }


        PageInfo<SysFrontendMenuTable> pageInfo = new PageInfo<>(sysFrontendDtos);
        List<SysFrontendMenuTable> list = pageInfo.getList();
        return R.ok(list);
    }


    @GetMapping("/getPermCode")
    public R getPermCode(HttpServletRequest request){
        User user = myUtils.TokenGetUserByName(request);
        String userId = user.getUserId();
//        String userId = "182730102";
        //实际是角色id
        List<SysRoleUserTable> sysRoleUserTables = sysRoleUserTableService.selectByUserId(userId);
        Set<Long> permList = new HashSet<>();
        //根据PERM与type查询对应的数据
        for (SysRoleUserTable sysRoleUserTable : sysRoleUserTables) {
            List<Long>  list= sysRoleFrontendMenuTableService.selectByRoleAndType(sysRoleUserTable.getRoleId(),"PERM");
            permList.addAll(list);
        }


        return R.ok(permList);
    }



    @PostMapping("/insert")
    @ApiOperation(value = "插入菜单信息", notes = "需要前端传入body和请求头")
    @Transactional(rollbackFor = Exception.class)
    public R InsertFrontMenu(@RequestBody SysFrontendDto sysFrontendDto,HttpServletRequest request) {
        if (sysFrontendDto.getLabel() == null) {
            return R.failed("填写为空信息");
        }
        //角色号
        User user = myUtils.TokenGetUserByName(request);
        String roleId = user.getUserId();
        //数据插入菜单表
        SysFrontendMenuTable sysFrontendMenuTable = sysFrontendMenuTableService.F_DtoToF_Po(sysFrontendDto);
        Long id = IDUtils.CreateId();
        sysFrontendMenuTable.setId(id);
        //请求头来获取角色id 创建者
        sysFrontendMenuTable.setCreatedBy(Long.valueOf(roleId));
        sysFrontendMenuTable.setCreateTime(DateKit.getNow());
        sysFrontendMenuTable.setUpdateTime(DateKit.getNowTime());
        sysFrontendMenuTable.setComponent(sysFrontendDto.getComponent());
        sysFrontendMenuTable.setRedirect(sysFrontendDto.getRedirect());
        JSONObject routeMeta = new JSONObject();
        String jsonStr = null;
        try {
            routeMeta = sysFrontendDto.getRouteMeta();
            jsonStr = JSONObject.toJSONString(routeMeta);
        } catch (Exception ignore) {
        }

        System.out.println(routeMeta);
        System.out.println(jsonStr);
        sysFrontendMenuTable.setRouteMeta(jsonStr);
        //判断path是否重复
        if (!Duplicate_Path(sysFrontendMenuTable)) {
            return R.failed("Path重复，插入失败");
        }
        //完成一次菜单表插入
        int i = sysFrontendMenuTableService.insertSelective(sysFrontendMenuTable);
        if (i <= 0) {
            return R.failed("插入失败");
        }

        return R.ok("插入成功");
    }



    @PostMapping("/update")
    @ApiOperation(value = "更新菜单信息", notes = "需要前端传入body和请求头")
    @Transactional(rollbackFor = Exception.class)
    public R UpdateFrontMenu(@RequestBody SysFrontendDto sysFrontendDto,HttpServletRequest request){
        if(sysFrontendDto == null || sysFrontendDto.getLabel() == null){
            return R.ok("填写为空信息");
        }
        User user = myUtils.TokenGetUserByName(request);
        String roleId = user.getUserId();
        SysFrontendMenuTable frontendMenuTable = sysFrontendMenuTableService.F_DtoToF_Po(sysFrontendDto);
        frontendMenuTable.setId(sysFrontendDto.getId());
        frontendMenuTable.setUpdateTime(DateKit.getNowTime());
        frontendMenuTable.setUpdatedBy(Long.valueOf(roleId));
        frontendMenuTable.setComponent(sysFrontendDto.getComponent());
        frontendMenuTable.setRedirect(sysFrontendDto.getRedirect());
        JSONObject routeMeta = new JSONObject();
        String jsonStr = null;
        try {
            routeMeta = sysFrontendDto.getRouteMeta();
            jsonStr = JSONObject.toJSONString(routeMeta);
        } catch (Exception ignore) {
        }
        frontendMenuTable.setRouteMeta(jsonStr);
        int i = sysFrontendMenuTableService.updateByPrimaryKeySelective(frontendMenuTable);
        //完成插入角色菜单表
        if ( i <=0 ){
            return R.failed("更新失败");
        }
        return R.ok("更新成功");
    }



    @PostMapping("/delete")
    @ApiOperation(value = "删除菜单信息", notes = "需要前端传送一个id号")
    public R DeleteFrontMenu(@RequestBody SysFrontendDto sysFrontendDto){
        //如果是body则就这样做
        if (sysFrontendDto.getId()==null){
            return R.failed("删除失败,未选择");
        }
        int i = sysFrontendMenuTableService.deleteByPrimaryKey(sysFrontendDto.getId());
        //删除前端表，中间表会出现查询空数据，是否对应删除  别删除可能后面要还原
        int j = sysRoleFrontendMenuTableService.deleteByAuthorityId(sysFrontendDto.getId());

        //完成插入角色菜单表
        if ( i <=0){
            return R.failed("删除失败");
        }
        return R.ok("删除成功");
    }


    @GetMapping("/getMenuAllList")
    @ApiOperation(value = "返回所有初始菜单", notes = "不需要任何信息")
    public R getAllMenu() {
        //树状结构
        List<SysFrontendMenuTable> sysFrontendMenuTables = sysFrontendMenuTableService.selectByAll();
        List<SysFrontendMenuTable> sysFrontendDtos = new ArrayList<>();

        //test
        for (SysFrontendMenuTable frontendMenuTable : sysFrontendMenuTables) {
            //加一个判断，判断表中的父类id是否为0                                                                                             getAuthorityId == front menu id
            if (frontendMenuTable.getParentId() == 0){
                List<SysFrontendMenuTable> sysFrontendMenu = sysFrontendMenuTableService.listWithTree(frontendMenuTable.getId());
                for (SysFrontendMenuTable frontendMenu : sysFrontendMenu) {
                    frontendMenu.setSortValue(frontendMenuTable.getSortValue());
                    sysFrontendDtos.add(frontendMenu);
                }
            }
        }

        return R.ok(sysFrontendDtos);
    }

    @GetMapping("/findMenu")
    @ApiOperation(value = "模糊查询菜单", notes = "")
    public R findMenu(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer state) {

        System.out.println(state);
        //树状结构
        List<SysFrontendMenuTable> sysFrontendMenuTables = sysFrontendMenuTableService.findMenu(name,state);
        if (sysFrontendMenuTables == null) {
            return R.ok(null);
        }
        List<SysFrontendMenuTable> sysFrontendDtos = new ArrayList<>();

        for (SysFrontendMenuTable frontendMenuTable : sysFrontendMenuTables) {
            //加一个判断，判断表中的父类id是否为0                                                                                             getAuthorityId == front menu id
            if (frontendMenuTable.getParentId() == 0){
                List<SysFrontendMenuTable> sysFrontendMenu = sysFrontendMenuTableService.listWithTree(frontendMenuTable.getId());
                for (SysFrontendMenuTable frontendMenu : sysFrontendMenu) {
                    frontendMenu.setSortValue(frontendMenuTable.getSortValue());
                    sysFrontendDtos.add(frontendMenu);
                }
            }
        }

        return R.ok(sysFrontendDtos);
    }



    public  Boolean Duplicate_Path(SysFrontendMenuTable frontendMenuTable){
        List<SysFrontendMenuTable> sysFrontendMenuTables = sysFrontendMenuTableService.selectByAll();
        for (SysFrontendMenuTable sysFrontendMenuTable : sysFrontendMenuTables) {
            if (frontendMenuTable.getPath().equals(sysFrontendMenuTable.getPath())) {
                return false;
            }
        }
        return true;
    }



}




