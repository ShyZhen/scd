package com.litblc.shiro.config;

import com.litblc.shiro.security.CustomUserDetailService;
import com.litblc.shiro.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 自定义过滤器
 * 继承 OncePerRequestFilter 类，并重写 doFilterInternal 方法
 */
@Component
@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            logger.info("config:jwtAuthFilter:doFilterInternal:start");

            // 其他接口验证 Token
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                logger.info("config:jwtAuthFilter:doFilterInternal:token不存在");

                filterChain.doFilter(request, response);
                return;
            }

            logger.info("存在token");

            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                logger.info("config:jwtAuthFilter:doFilterInternal:token验证成功");

                String username = jwtUtils.getUsernameFormToken(token);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);  // 调用了CustomUserDetailService，也就是实现security的UserDetailsService

                // 设置安全上下文，如果是有效的jwt，那么设置该用户为认证后的用户
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(  // 用户的认证信息
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // 设置到SecurityContextHolder,方便后续访问到用户的认证信息
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

            filterChain.doFilter(request, response);

        } catch (AuthenticationException  ex) {
            // 配置exceptionHandling后，过滤器的doFilterInternal方法无法直接捕获到认证异常
            logger.info("doFilter:catch无法捕获到异常");
            throw ex;
        }

    }
}
