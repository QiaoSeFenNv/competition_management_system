package com.qiaose.competitionmanagementsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.qiaose.competitionmanagementsystem.mapper")
public class CompetitionManagementSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(CompetitionManagementSystemApplication.class, args);
    }

}
