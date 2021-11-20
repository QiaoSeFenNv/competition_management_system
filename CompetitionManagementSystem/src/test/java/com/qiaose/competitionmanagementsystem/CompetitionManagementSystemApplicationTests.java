package com.qiaose.competitionmanagementsystem;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
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
    public void getCurMenu(){
//        SysFrontendMenuTable sysFrontendMenuTable = sysFrontendMenuTableService.selectByPrimaryKey(1L);
//        List<SysFrontendMenuTable> sysFrontendMenuTables = sysFrontendMenuTableService.selectByParentId(sysFrontendMenuTable.getId());
//        sysFrontendMenuTable.setChildrenList();
//        SysFrontendDto sysFrontendDto = sysFrontendMenuTableService.F_PoToDto(sysFrontendMenuTable);

//        //最外层变为数组
//        List<SysFrontendDto> result = new ArrayList<>();
//        result.add(sysFrontendDto);
//
//        System.out.println(R.ok(result));

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
