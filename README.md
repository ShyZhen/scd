# scd
spring cloud demo

### 打包构建
- 在scd目录下执行mvn命令`mvn clean package`构建

### 项目执行
- windows示例
```
@echo off

start D:\wamp\jdk-17_windows-x64_bin\jdk-17.0.5\bin\java.exe -jar E:\githubShyzhen\scd\config\target\config.jar

timeout /t 10 > nul

start D:\wamp\jdk-17_windows-x64_bin\jdk-17.0.5\bin\java.exe -jar E:\githubShyzhen\scd\fmock\target\fmock.jar

timeout /t 10 > nul

start D:\wamp\jdk-17_windows-x64_bin\jdk-17.0.5\bin\java.exe -jar E:\githubShyzhen\scd\push\target\push.jar --MAIL_PASSWORD=xxxxx

@pause
```

### 多模块访问测试
- config http://localhost:8888/fmock/default
- fmock http://localhost:8081/fmock/listDesc
- shiro http://localhost:8082/authtest/test
- push http://localhost:8083/api/test/config
- swagger http://localhost:8082/swagger-ui/index.html

### 设置反向代理示例
```
    server {
        listen       8082;
        server_name  scd.com;
        location / {
            proxy_pass http://localhost:8082;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }
```

### 计划 DONE & TODO
// DONE
- 结构优化：参考spring-boot，弄个dependencies，最外层只管理模块，然后有单独的依赖管理（project下有a，b，c，parent，四个modules，都要继承自parent么？是的！）
- 配置中心
- 调用别的模块的方法
- 单模块/多模块打包

// TODO
- 登录、鉴权相关功能，包括session以及token两种模式
- 注册中心nacos、Consul
- 模块服务调用openfeign
- 整套微服务部署
- 统一处理错误信息和状态码
