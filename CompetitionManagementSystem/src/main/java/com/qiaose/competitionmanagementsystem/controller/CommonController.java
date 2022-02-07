package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.generator.Generator;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.SchedulerMail;
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

import javax.validation.constraints.Email;
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

    @Autowired
    SchedulerMail schedulerMail;

    @GetMapping("/sendCode")
    @ApiOperation(value = "发送验证码",notes = "需要输入邮箱地址")
    public R sendCode(@RequestParam @Email String email){

        RandomGenerator randomGenerator = new RandomGenerator(6);
        //验证码6位
        String generate = randomGenerator.generate();

        //设置邮件内容
        String mailText = "【竞赛管理系统】 验证码："+generate+"  请勿将验证码告诉他人哦。";

        try{
            String s = stringRedisTemplate.opsForValue().get(email);
            if (s!=null){
                return R.failed("邮箱时间还未过90秒");
            }
            if(!email.isEmpty()){
                schedulerMail.toMail(email,mailText);
                stringRedisTemplate.opsForValue().set(email,generate,120, TimeUnit.SECONDS);
                return R.ok("");
            }
        }catch (Exception exception){
            throw  exception;
        }

        return R.failed("发送成功");

    }

}
