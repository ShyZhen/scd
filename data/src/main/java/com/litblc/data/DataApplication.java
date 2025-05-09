package com.litblc.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

// 需显式扫描 common 模块的包(因为使用了common的result类和exception自动捕获)
@ComponentScan(basePackages = {
        "com.litblc.data",     // 显式包含主模块包
        "com.litblc.common"    // common 模块的包
})
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }

}
