package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.components.SchedulerMail;
import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import com.qiaose.competitionmanagementsystem.entity.CompetitionTodo;
import com.qiaose.competitionmanagementsystem.entity.dto.CommonDto;
import com.qiaose.competitionmanagementsystem.enums.TodoStateEnum;
import com.qiaose.competitionmanagementsystem.service.CompetitionRecordService;
import com.qiaose.competitionmanagementsystem.service.CompetitionTodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@Api(value = "公共接口")
@RequestMapping("/common")
public class CommonController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource
    CompetitionTodoService competitionTodoService;

    @Resource
    CompetitionRecordService competitionRecordService;

    @Autowired
    SchedulerMail schedulerMail;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

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

    @GetMapping("/templateData")
    @ApiOperation(value = "获取前端模板",notes = "需要输入邮箱地址")
    public R sendCode(HttpServletRequest request){
        CommonDto commonDto = new CommonDto();
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApplicantId(username);
        //拿到今天接收的申请
        List<CompetitionTodo> receive = competitionTodos.stream().filter(competitionTodo -> (
                DateUtil.format(competitionTodo.getCreateTime(),"yyyy-mm-dd").equals(DateUtil.format(new Date(),"yyyy-mm-dd"))
                        && (Objects.equals(competitionTodo.getTodoStatus(), TodoStateEnum.IN_PROGRESS.getCode())))).collect(Collectors.toList());
        commonDto.setToDayReceive(receive.size());
        //拿到今天处理的申请
        List<CompetitionTodo> deal = competitionTodos.stream().filter(competitionTodo -> (
                DateUtil.format(competitionTodo.getCreateTime(),"yyyy-mm-dd").equals(DateUtil.format(new Date(),"yyyy-mm-dd"))
                        && (Objects.equals(competitionTodo.getTodoStatus(), TodoStateEnum.AGREE.getCode()) || Objects.equals(competitionTodo.getTodoStatus(), TodoStateEnum.DISAGREE.getCode()))
        )).collect(Collectors.toList());
        commonDto.setToDayReceive(deal.size());

        List<CompetitionRecord> sumPrice= competitionRecordService.selectByUserId(username);
        commonDto.setSumPrice(sumPrice.size());

        return R.ok(commonDto);
    }
}
