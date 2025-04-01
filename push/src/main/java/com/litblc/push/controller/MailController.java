package com.litblc.push.controller;

import com.litblc.push.utils.SendMailUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhenhuaixiu
 * @Date 2023/11/13 10:38
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/mail")
public class MailController {

    @Value("${spring.mail.from-addr}")
    private String fromAddr;

    @Autowired
    SendMailUtils sendMailUtils;

    @Operation(summary = "测试发邮件")
    @GetMapping(value = "/config")
    public String getConfig() {
        return this.fromAddr;
    }

    @Operation(summary = "测试发邮件")
    @GetMapping(value = "/send")
    public void send() {
        this.sendMailUtils.send("835433343@qq.com", "subject 是标题", "text 是内容2");
    }
}
