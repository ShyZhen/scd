package com.litblc.shiro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litblc.shiro.common.Result.HttpStatusEnum;
import com.litblc.shiro.common.Result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义过滤器token认证失败时返回401
 * 全局异常处理无法捕获security认证时的异常，非控制器异常
 * 可以读一下 https://blog.csdn.net/m0_74065705/article/details/142468012
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        System.out.println("自定义过滤器token认证失败时返回401");

        // 1. 设置响应格式
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // 2. 生成统一错误响应
        // 统一响应格式
        Result<?> result = Result.fail(HttpStatusEnum.HTTP_UNAUTHORIZED.getCode(), "认证失败:" + authException.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
