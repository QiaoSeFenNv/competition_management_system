package com.qiaose.competitionmanagementsystem;

import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class CompetitionManagementSystemApplicationTests {


    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

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
