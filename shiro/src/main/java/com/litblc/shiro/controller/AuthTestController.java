package com.litblc.shiro.controller;

import com.litblc.shiro.common.Result.Result;
import com.litblc.shiro.entity.Posts;
import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.PostsMapper;
import com.litblc.shiro.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/authtest")
public class AuthTestController {

    @Autowired
    public IUserService iUserService;

    @Autowired
    private PostsMapper postsMapper;

    @Value("${spring.application.name}")
    public String appName;

    @Value("${spring.application.desc:默认描述}")
    public String appDesc;

    @Operation(summary = "测试使用配置")
    @GetMapping(value = "/test")
    public String getConfig() {
        return this.appName + "----" + this.appDesc;
    }


    @Operation(summary = "测试使用别的数据库")
    @GetMapping(value = "/changedb")
    public String changeDb() {

        int userCount = this.postsMapper.getAllCount();
        String userName = this.postsMapper.getUserName(1);
        List<Posts> res = this.postsMapper.getList(4);
        List<Posts> list = this.postsMapper.getAll();

        System.out.println(userCount);
        System.out.println(userName);
        System.out.println(res);
        System.out.println(list);

        String userName2 = this.postsMapper.getUserName(2);
        return userName2;
    }

    @Operation(summary = "三种接收参数测试")
    @PostMapping(value = "/raw/{path_id}/{sort_type}")
    public void raw(
            @PathVariable(value = "path_id") @Parameter(description = "path参数可以多个") long pathId,
            @PathVariable(value = "sort_type") @Parameter(description = "path参数") String sort_type,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Parameter(description = "url参数可以多个") long page,
            @RequestParam(value = "page_size", required = false, defaultValue = "15") @Parameter(description = "url参数") long pageSize
    ) {

        // 添加raw参数验证
        System.out.println(pathId);
        System.out.println(sort_type);
        System.out.println(page);
        System.out.println(pageSize);

    }

    @Operation(summary = "注册用户")
    @PostMapping(value = "/register")
    public Users registerUser(
            @RequestParam(value = "email", required = true) @Parameter(description = "url参数可以多个") String email,
            @RequestParam(value = "mobile") @Parameter(description = "url参数可以多个") String mobile,
            @RequestParam(value = "password") @Parameter(description = "url参数可以多个") String password,
            @RequestParam(value = "name") @Parameter(description = "url参数可以多个") String name,
            @RequestParam(value = "gender", defaultValue = "1") @Parameter(description = "url参数可以多个") String gender
    ) {
        String uuid = "uuid-xxx";

        Users users = new Users();
        users.setUuid(uuid).setName(name).setEmail(email).setMobile(mobile).setGender(gender).setPassword(password);

        return users;
    }

    @Operation(summary = "使用返回类")
    @GetMapping(value = "/home")
    public Result<String> home() {
        String str = "登录页11";

        return Result.success(str,str);
    }

    @GetMapping(value = "/home2")
    public Result<?> data() {
        String str = "登录页";
        ArrayList list = new ArrayList();
        list.add(str);
        list.add(555);

        return Result.successWithData(list);
    }
}
