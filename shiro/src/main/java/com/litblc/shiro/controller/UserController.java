package com.litblc.shiro.controller;

import com.litblc.shiro.entity.Posts;
import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.PostsMapper;
import com.litblc.shiro.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    // 新的加密方式
    PasswordEncoder DelegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

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


    @Operation(summary = "测试新的加密方式")
    @GetMapping("/{id}/status")
    public long getUserStatus(@PathVariable Long id) {

        String pw = DelegatingPasswordEncoder.encode("123455");
        System.out.println(pw);

        return id;
    }
}

