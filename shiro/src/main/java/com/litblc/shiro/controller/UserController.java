package com.litblc.shiro.controller;

import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.UserMapper;
import com.litblc.shiro.security.CustomUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "测试使用别的数据库")
    @GetMapping(value = "/get/{id}")
    public Users getUser(
            @PathVariable(value = "id") @Parameter(description = "id") long id
    ) {

        Users userInfo = this.userMapper.selectById(id);
        System.out.println(userInfo);

        return userInfo;
    }


    @Operation(summary = "需要登录的接口，获取用户信息")
    @GetMapping("/info")
    public String getUserInfo() {
        System.out.println("userController:需要登录的方法，获取当前登录用户信息");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //String username = authentication.getName();

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetail.getUserId();
        String username = userDetail.getUsername();

        System.out.println("通过token获取用户ID:"+userId);

        return username+"---"+userId;

    }
}

