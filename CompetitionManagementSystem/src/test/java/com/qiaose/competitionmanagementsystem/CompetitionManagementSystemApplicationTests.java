package com.qiaose.competitionmanagementsystem;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.controller.UserController;
import com.qiaose.competitionmanagementsystem.entity.SysBackendApiTable;
import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.User;

import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import com.qiaose.competitionmanagementsystem.entity.dto.UserDto;
import com.qiaose.competitionmanagementsystem.service.SysBackendApiTableService;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import com.qiaose.competitionmanagementsystem.utils.TaleUtils;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CompetitionManagementSystemApplicationTests {


    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    SysFrontendMenuTableService sysFrontendMenuTableService;

    @Autowired
    UserController controller;


    @Autowired
    SysBackendApiTableService sysBackendApiTableService;



    @Test
    public void mapTest(){
        ModelMapper modelMapper = new ModelMapper();
        User user = new User();
        UserDto map = modelMapper.map(user, UserDto.class);
        System.out.println(map);
    }


    @Test
    public void Page(){

        PageHelper.startPage(1,2);
        List<SysBackendApiTable> sysBackendApiTables = sysBackendApiTableService.selectByAll();
        PageInfo<SysBackendApiTable> pageInfo = new PageInfo<>(sysBackendApiTables);
        System.out.println(pageInfo);

    }

    @Test
    public void Json(){
        SysFrontendMenuTable sysFrontendMenuTable = new SysFrontendMenuTable();
        sysFrontendMenuTable.setId(1L);
        sysFrontendMenuTable.setDescribe("10");
        sysFrontendMenuTable.setLabel("ss");
        String s = TaleUtils.filterFieldsJson(sysFrontendMenuTable, SysFrontendMenuTable.class, "path", "id");
        System.out.println(s);
    }


    @Test
    public void Register(){
        User user = new User();
        user.setPassword("123456");
        user.setAccountName("lisi");
        R register = controller.register(user);
        System.out.println(register);
    }


    @Test
    void BCry(){
        String encode = bCryptPasswordEncoderUtil.encode("000000");
        System.out.println(encode);

        System.out.println(bCryptPasswordEncoderUtil.matches("000000", "$2a$10$1H81ddXs9x/KqYC3IA.kBO7hFac5KXHz1Bgwt49GZbwvjXkXbLFTq"));
    }

    @Test
    void JwToken(){

        AuthUser userDetails = new AuthUser("admin","0000",null);

        System.out.println(jwtTokenUtil.generateToken(userDetails));

        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.generateToken(userDetails));
        System.out.println(usernameFromToken);
    }




    @Test
    public void PoDtoTest(){

        SysFrontendDto sysFrontendDto = new SysFrontendDto();
        sysFrontendDto.setDescribe("123465");
        sysFrontendDto.setPath("/**");
        sysFrontendDto.setLabel("nini");
        sysFrontendDto.setIcon("dfds");
        sysFrontendDto.setKeepAlive(false);
        sysFrontendDto.setSortValue(100);
        sysFrontendDto.setState("dsfs");
        sysFrontendDto.setParentId(100L);
        sysFrontendDto.setReadOnly(true);
        SysFrontendMenuTable sysFrontendMenuTable = sysFrontendMenuTableService.F_DtoToF_Po(sysFrontendDto);
        System.out.println(sysFrontendMenuTable);
    }

    @Test
    void contextLoads() {
    }

}
