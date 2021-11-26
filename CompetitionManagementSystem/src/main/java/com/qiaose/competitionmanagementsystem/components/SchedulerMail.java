package com.qiaose.competitionmanagementsystem.components;

import com.qiaose.competitionmanagementsystem.utils.MailSend;
import com.qiaose.competitionmanagementsystem.utils.TaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

//@Component
public class SchedulerMail {

    @Autowired
    MailSend mailSend;



//    @Scheduled(cron = "0 0 8 * * ?")
    public void proces() throws MessagingException{
        String [] to = {"642190034@qq.com","1450040534@qq.com"};
        mailSend.SendMail(to,"开始打工","你好，生活","表情包"
                , TaleUtils.getUplodFilePath()+"/images/EFCC27A17414CF24B74B989DE13F6E3F.jpg");
    }
}

