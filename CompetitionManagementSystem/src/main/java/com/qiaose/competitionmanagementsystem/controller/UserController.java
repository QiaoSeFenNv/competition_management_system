package com.qiaose.competitionmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.UserDto;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@RestController
@Api(value = "用户接口")
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SysRoleUserTableService sysRoleUserTableService;

    @Autowired
    SysRoleTableService sysRoleTableService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @GetMapping("/search")
    @ApiOperation(value="查询所有用户", notes="显示所有用户数据,封装未R.ok类型")
    public R getList() {
        List<User> list =  userService.list();
        return  R.ok(list);
    }



    @GetMapping("/getUserInfo")
    @ApiOperation(value="查询用户信息", notes="由前端请求头中获取token,在利用token获得用户信息")
    public R getUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);
        UserDto userDto = userService.PoToDto(user);
        userDto.setToken(token);
        SysRoleUserTable sysRoleUserTable = sysRoleUserTableService.selectByRoleId(user.getRoleId());
        List<SysRoleTable> sysRoleTables = sysRoleTableService.selectByPrimaryKey(sysRoleUserTable.getRoleId());
        HashMap<String,String> role = new HashMap<>();
        for (SysRoleTable sysRoleTable : sysRoleTables) {
            role.put("roleName",sysRoleTable.getRoleName());
            role.put("value",sysRoleTable.getRoleId());
        }
        List list = new ArrayList(Collections.singleton(role));
        userDto.setRoles(list);
        return R.ok(userDto);
    }


    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadFile(MultipartFile file, HttpServletRequest request) {
        try{
            //创建文件在服务器端的存放路径
            String dir=request.getServletContext().getRealPath("/upload");
            File fileDir = new File(dir);
            if(!fileDir.exists())
                fileDir.mkdirs();
            //生成文件在服务器端存放的名字
            String fileSuffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName= UUID.randomUUID().toString()+fileSuffix;
            File files = new File(fileDir+"/"+fileName);
            //上传
            file.transferTo(files);

        }catch(Exception e) {
            e.printStackTrace();
            return "上传失败";
        }

        return "上传成功";
    }








}
