package com.litblc.shiro.controller;

import com.litblc.shiro.Common.Exception.ServiceException;
import com.litblc.shiro.Common.Result.HttpStatusEnum;
import com.litblc.shiro.Common.Result.Result;
import com.litblc.shiro.dto.LoginRequest;
import com.litblc.shiro.dto.RegisterRequest;
import com.litblc.shiro.entity.Users;
import com.litblc.shiro.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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


    @Autowired
    public AuthService authService;

    @PostMapping(("/register"))
    public Result<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            Users users = authService.register(request);
            return Result.successWithData(users);
        } catch (Exception e) {
            return Result.fail(HttpStatusEnum.HTTP_UNPROCESSABLE_ENTITY.getCode(), "用户名已存在");
            // throw new ServiceException(e.getMessage());
        }
    }

    @PostMapping(("/login"))
    public Result<?> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        return Result.successWithData(token);
    }



}
