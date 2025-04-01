package com.litblc.shiro.controller;

import com.litblc.shiro.common.Result.Result;
import com.litblc.shiro.controller.base.BaseController;
import com.litblc.shiro.dto.response.UserResponseDto;
import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.UserMapper;
import com.litblc.shiro.security.CustomUserDetail;
import com.litblc.shiro.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息
 * @Author zhenhuaixiu
 * @Date 2023/11/29 15:31
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;

    @Operation(summary = "测试使用别的数据库")
    @GetMapping(value = "/get/{id}")
    public Result<Users> getUser(
            @PathVariable(value = "id") @Parameter(description = "id") long id
    ) {

        Users userInfo = this.userMapper.selectById(id);
        System.out.println(userInfo);

        return Result.successWithData(userInfo);
    }

    @GetMapping(value = "/getnew/{id}")
    public Result<UserResponseDto> getUserNew(
            @PathVariable(value = "id") @Parameter(description = "id") long id
    ) {

        UserResponseDto userInfo = this.userMapper.findByIdNew(id);
        return Result.successWithData(userInfo);
    }



    @Operation(summary = "需要登录的接口，获取用户信息")
    @GetMapping("/info")
    public Result<String> getUserInfo() {
        System.out.println("userController:需要登录的方法，获取当前登录用户信息");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //String username = authentication.getName();

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetail.getUserId();
        String username = userDetail.getUsername();

        log.info("通过token获取用户ID:"+userId);

        return Result.successWithData(username+"---"+userId);
    }

    @Operation(summary = "控制器中设置http状态码200,大部分都是200的请求,所以不需要指定ResponseEntity,默认200")
    @GetMapping("/info2")
    public ResponseEntity<Result<?>> getUserInfo2() {
        return ResponseEntity.ok(Result.successWithData("控制器中设置http状态码"));
    }

    @Operation(summary = "控制器中设置http状态码400")
    @GetMapping("/info3")
    public ResponseEntity<Result<?>> getUserInfo3() {
        return ResponseEntity.badRequest().body(Result.successWithData("控制器中设置http状态码400"));
    }

    @Operation(summary = "")
    @GetMapping("/info4")
    public ResponseEntity<Result<?>> getUserInfo4() {
        String username = "123456789ABC17";

        Users users = this.userService.findByUsername(username);
        return ResponseEntity.badRequest().body(Result.successWithData(users));
    }
}

