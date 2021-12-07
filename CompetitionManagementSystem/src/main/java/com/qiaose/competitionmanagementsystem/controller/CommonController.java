package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.generator.Generator;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.utils.MailSend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@Api(value = "公共接口")
@RequestMapping("/common")
public class CommonController {



    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sendCode")
    @ApiOperation(value = "发送二维码",notes = "需要输入邮箱地址")
    public R sendCode(@RequestParam String mailTo){

        RandomGenerator randomGenerator = new RandomGenerator(6);
        //验证码6位
        String generate = randomGenerator.generate();

        //设置邮件内容
        String mailText = "【竞赛管理系统】 验证码："+generate+"  请勿将验证码告诉他人哦。";



        if(!mailTo.isEmpty()){
            toMail(mailTo,mailText);
            stringRedisTemplate.opsForValue().set(mailTo,generate,30, TimeUnit.SECONDS);
            return R.ok("");
        }

        return R.failed("");

    }



    /**
     * 发送邮件，公共方法
     * @param mailTo
     * @param mailText
     */
    public void toMail(String mailTo,String mailText){
        //定义发送标题
        String title = "农产品市场";
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
