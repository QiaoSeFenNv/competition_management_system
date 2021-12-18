package com.qiaose.competitionmanagementsystem.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SchedulerMail {


    @Autowired
    JavaMailSender javaMailSender;


    public void toMail(String mailTo,String mailText){
        //定义发送标题
        String title = "竞赛管理系统";
        //设置发送方
        String mailFrom = "qiaosefennv@163.com";
        try {
            //构建邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //设置邮件主题
            message.setSubject(title);
            //设置邮件发送者
            message.setFrom(mailFrom);
            //设置邮件接收者
            message.setTo(mailTo);
            //设置邮件发送日期
            message.setSentDate(new Date());
            //设置邮件正文
            message.setText(mailText);
            //发送邮件
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}

