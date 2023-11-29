package com.litblc.shiro.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Value("${spring.application.name}")
    public String appName;

    @Operation(summary = "测试使用配置")
    @GetMapping(value = "/test")
    public String getConfig() {
        return this.appName;
    }
}
