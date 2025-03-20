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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT过滤器
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

        System.out.println("config:jwtAuthFilter:doFilterInternal:start");

        // 检查请求是公开的直接放行
//        String requestURI = request.getRequestURI();
//        if (requestURI.startsWith("/auth/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 其他接口验证 Token
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            System.out.println("config:jwtAuthFilter:doFilterInternal:token是空的");

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("token不是空的");

        String token = authHeader.substring(7);
        if (jwtUtils.validateToken(token)) {
            System.out.println("config:jwtAuthFilter:doFilterInternal:token验证成功");

            String username = jwtUtils.getUsernameFormToken(token);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);  // 调用了CustomUserDetailService，也就是实现security的UserDetailsService

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    )
            );
        }

        filterChain.doFilter(request, response);
    }
}
