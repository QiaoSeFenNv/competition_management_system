package com.qiaose.competitionmanagementsystem;

import cn.hutool.core.date.DateUtil;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CompetitionManagementSystemApplicationTests {


//    @Autowired
//    JavaMailSenderImpl javaMailSender;
//
//
//    @Test
//    void contextLoads() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("开始工作了");
//        message.setText("打工人的生活正式开始，今天是"+ DateUtil.date()+"天气良好，是打工的美好一天");
//        message.setFrom("qiaosefennv@163.com");
//        message.setTo("642190034@qq.com");
//        javaMailSender.send(message);
//    }

}
