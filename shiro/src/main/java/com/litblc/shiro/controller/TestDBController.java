package com.litblc.shiro.controller;

import com.litblc.shiro.entity.Posts;
import com.litblc.shiro.mapper.PostsMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/authDB")
public class TestDBController {

    @Autowired
    private PostsMapper postsMapper;

    @Value("${spring.application.name}")
    public String appName;

    @Value("${spring.application.desc:默认描述}")
    public String appDesc;

    @Operation(summary = "测试使用配置")
    @GetMapping(value = "/test")
    public String getConfig() {
        return this.appName+"----"+this.appDesc;
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


    @GetMapping("/users/{id}/status")
    public Status getUserStatus(@PathVariable Long id) {
        // Logic to retrieve user status from the database

        /* 示例-Status.valueOf() 方法用于将数据库中的字符串值转换为枚举常量
        @Entity
        public class User {
            // Other fields

            @Enumerated(EnumType.STRING)
            private Status status;

            // Getters and setters
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Status status = Status.valueOf(user.getStatus().toUpperCase());
            // 使用 status 枚举常量进行后续操作
        }
        */

        // return Status.ACTIVE;
        return Status.valueOf("INACTIVE");
    }
}


// 测试枚举
enum Status {
    ACTIVE,
    INACTIVE,
    PENDING
}