# scd
spring cloud 系统集成与架构

# 框架代码说明
### 1. common
- 提供可复用的代码(工具类、通用枚举、DTO等)，纯代码，无自动配置，仅需标准代码结构，用于跨模块共享通用代码。存放与业务强相关的通用代码。
### 2. config
- 代码框架的全局配置，所有的springboot应用项目都需要引用该模块，进行统一的配置管理。Springboot应用支持两种格式的配置文件：yaml格式以及properties格式，且properties格式的配置优先于yaml格式的配置，这里使用yaml的便于后续被properties覆盖。
### 3. parent
- 代码框架的父pom，主要作用是代码依赖版本的统一管理。所有模块都要引入该pom作为父pom，并且各模块下代码依赖的版本都需要在该pom中进行管理，不能在其他模型下直接引入版本号。
### 4. framework
- 用于配置核心服务的Starter包(如Redis自动配置RedisTemplate、MQ、Log、数据库等)，提供开箱即用的自动化配置和依赖管理。Starter应保持技术中立，只处理技术组件的配置。

# 集成
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
timeout /t 10 > nul

start D:\wamp\jdk-17_windows-x64_bin\jdk-17.0.5\bin\java.exe -jar E:\githubShyzhen\scd\push\target\data.jar --DRUID_USER=xxxxx --DRUID_PW=xxxxx
timeout /t 10 > nul

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

# 计划 DONE & TODO
// DONE
- 结构优化：参考spring-boot，弄个dependencies，最外层只管理模块，然后有单独的依赖管理（project下有a，b，c，parent，四个modules，都要继承自parent么？是的！）
- 配置中心
- 调用别的模块的方法
- 单模块/多模块打包
- 统一处理错误信息和状态码


// TODO
- 登录、鉴权相关功能，包括session以及token两种模式
- 注册中心nacos、Consul
- 模块服务调用openfeign
- 整套微服务部署
