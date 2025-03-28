package com.litblc.shiro.config;

import com.litblc.shiro.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // 启用 Spring Security 的 web 安全功能
// @RequiredArgsConstructor 写了构造函数注入，就不用这个了。
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, CustomUserDetailService customUserDetailService, AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.customUserDetailService = customUserDetailService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // 基于token，不需要csrf
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        /* 明确指出需要放行的，其他都要认证
                        .requestMatchers("/auth/**").permitAll()        // 放行api
                        .requestMatchers("/api/public/**").permitAll()  // 放行api
                        .requestMatchers("/error", "/swagger-ui/**", "/v3/api-docs/**").permitAll()  // 放行api
                        .requestMatchers("/authtest/**","/test/**").permitAll()                        // 放行api
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()  // 放行api
                        .anyRequest().authenticated()                     // 其他接口需认证
                         */

                        // 明确指定需要认证的，其他路由(包括不存在的)都放行，可以触发默认error响应
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()  // 放行api
                        .anyRequest().permitAll()                         // 其他接口都放行
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // 基于token，不需要session
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)               // 注册自定义的jwt过滤器
                .exceptionHandling(e -> e                                                                 // 配置异常处理，自动返回401 (配置exceptionHandling后，过滤器的doFilterInternal方法无法直接捕获到认证异常)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .userDetailsService(customUserDetailService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 也可用有参构造，取值范围是 4 到 31，默认值为 10。数值越大，加密计算越复杂
        return new BCryptPasswordEncoder();
    }

    // 新增Bean：暴露 AuthenticationManager添加到spring容器
    // 该方法是实际执行身份验证的过程；而SecurityContextHolder.getContext().getAuthentication()是认证后获取当前用户信息
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
