package com.litblc.push.controller;

import com.litblc.common.requestBean.test.TestRaw;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 测试使用别的模块的服务、方法
 * 总结：是不行的，毕竟不是一个jar包,想要访问其他模块的服务，只能通过http请求，使用openfeign包
 * common模块或者其他模块能使用，是因为它就是单独的代码，并没有启动类，没启动服务，所以没进入容器，无法使用注解，不涉及IP和端口之类的
 *
 * @Author zhenhuaixiu
 * @Date 2023/10/24 11:08
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Value("${spring.application.name}")
    public String appName;

    @Operation(summary = "测试使用配置")
    @GetMapping(value = "/config")
    public String getConfig() {
        return this.appName;
    }

    @Operation(summary = "三种接收参数测试")
    @PostMapping(value = "/raw/{path_id}/{sort_type}")
    public TestRaw raw(
            @PathVariable(value = "path_id") @Parameter(description = "path参数可以多个") long pathId,
            @PathVariable(value = "sort_type") @Parameter(description = "path参数") String sort_type,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Parameter(description = "url参数可以多个") long page,
            @RequestParam(value = "page_size", required = false, defaultValue = "15") @Parameter(description = "url参数") long pageSize,
            @RequestBody TestRaw testRaw
            ) {

        // 添加raw参数验证
        testRaw.validator();

        System.out.println(pathId);
        System.out.println(sort_type);
        System.out.println(page);
        System.out.println(pageSize);
        System.out.println(testRaw.nickname);

        return testRaw;
    }
}
