package com.qiaose.competitionmanagementsystem.utils;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailSend {
    @Autowired
    JavaMailSenderImpl javaMailSender;

    public void SendMail(String[] to,String subject,String content,String filename,String filepath ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject(subject);//标题
        helper.setText("<b><h1 style='color:red;'>"+content+"</h1></b>",true);//内容，可以用html设置样式，但是必须是true，默认是false
        helper.addAttachment(filename,new File(filepath));//附件，filename：在邮件中展示附件的名字，filepath在当前机器中的路径
        helper.setFrom("qiaosefennv@163.com");//发送人
        helper.setTo(to);
        javaMailSender.send(mimeMessage);
    }
}
