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