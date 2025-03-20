package com.litblc.shiro.controller;

import com.litblc.shiro.entity.Users;
import com.litblc.shiro.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/authtest")
public class AuthTestController {
    @Value("${spring.application.name}")
    public String appName;

    @Operation(summary = "测试使用配置")
    @GetMapping(value = "/test")
    public String getConfig() {
        return this.appName;
    }


    @Autowired
    public IUserService iUserService;

    @Operation(summary = "注册用户")
    @PostMapping(value = "/register")
    public Users registerUser(
            @RequestParam(value = "email", required = true) @Parameter(description = "url参数可以多个") String email,
            @RequestParam(value = "mobile") @Parameter(description = "url参数可以多个") String mobile,
            @RequestParam(value = "password") @Parameter(description = "url参数可以多个") String password,
            @RequestParam(value = "name") @Parameter(description = "url参数可以多个") String name,
            @RequestParam(value = "gender", defaultValue = "1") @Parameter(description = "url参数可以多个") String gender
    ) {
        String uuid = "uuid-1111111";

        Users users = new Users();
        users.setUuid(uuid).setName(name).setEmail(email).setMobile(mobile).setGender(gender).setPassword(password);

        iUserService.registerUser(users);

        return users;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "get-login";
    }

    @PostMapping("/dologin")
    public Users doLogin(
            @RequestParam(value = "name") @Parameter(description = "") String name
    ) {
        System.out.println("post-login");

        Users users = iUserService.findByUsername(name);
        System.out.println(users);

        return users;
    }

    @Operation(summary = "登录成功后跳转的地方")
    @GetMapping(value = "/home")
    public String home() {
        String str = "登录页";

        return str;
    }
}
