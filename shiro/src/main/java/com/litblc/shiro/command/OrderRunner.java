package com.litblc.shiro.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，SpringApplication.run() 之前执行
 * 非常适合在应用程序启动之初进行一些数据初始化的工作
 *
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:05
 * @Version 1.0
 */
@Component
@Order(1)
public class OrderRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("一些初始化任务");
    }
}

@Component
@Order(2)
class Order2Runner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println(" @Order 注解的实现类最先执行，并且@Order()里面的值越小启动越早");
    }
}