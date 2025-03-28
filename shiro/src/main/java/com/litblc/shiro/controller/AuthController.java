package com.litblc.shiro.controller;

import com.litblc.shiro.common.Exception.ServiceException;
import com.litblc.shiro.common.Result.Result;
import com.litblc.shiro.controller.base.BaseController;
import com.litblc.shiro.dto.request.LoginRequestDto;
import com.litblc.shiro.dto.request.RegisterRequestDto;
import com.litblc.shiro.entity.Users;
import com.litblc.shiro.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 保持RESTful风格又减少ResponseEntity:
 * 对成功场景直接返回对象
 * 对失败场景抛出异常，由全局处理器转换为错误响应
 *
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {
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
    public ResponseEntity<Result<?>> register(@RequestBody @Valid RegisterRequestDto request) {
        try {
            Users users = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Result.successWithData(users));
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @PostMapping(("/login"))
    public Result<?> login(@RequestBody @Valid LoginRequestDto request) {
        try {
            String token = authService.login(request);
            return Result.successWithData(token);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }



}
