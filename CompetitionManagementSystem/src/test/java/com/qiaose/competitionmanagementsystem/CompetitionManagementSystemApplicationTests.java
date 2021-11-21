package com.qiaose.competitionmanagementsystem;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import com.qiaose.competitionmanagementsystem.utils.TaleUtils;
import org.junit.jupiter.api.Test;
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
    void contextLoads() {
    }

}
