package com.litblc.push.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Author zhenhuaixiu
 * @Date 2023/11/13 10:56
 * @Version 1.0
 */
@Component
public class SendMailUtils {

    @Value("${spring.mail.from-addr}")
    private String fromAddr;

    @Autowired
    JavaMailSender mailSender;

    public void send(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddr);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        this.mailSender.send(mailMessage);
    }
}
