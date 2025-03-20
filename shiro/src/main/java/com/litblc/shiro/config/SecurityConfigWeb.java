package com.litblc.shiro.config;

//import com.litblc.shiro.security.CustomUserDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private final CustomUserDetailService customUserDetailService;
//
//    public SecurityConfig(CustomUserDetailService customUserDetailService) {
//        this.customUserDetailService = customUserDetailService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/auth/register").permitAll()
//                        .requestMatchers("/auth/login").permitAll()
//                        .requestMatchers("/auth/dologin","auth/test","/authDB/users/*/status","/swagger-ui/**","/v3/api-docs/**").permitAll()  // permitAll代表不需要认证
//                        .anyRequest().authenticated()  // 其他路由都需要认证
//                )
//                .formLogin(form -> form
//                        .loginPage("/auth/login") // 自定义登录页面
//                        .defaultSuccessUrl("/auth/home")
//                        .permitAll()              // 允许所有用户访问登录页面
//                ).logout(LogoutConfigurer::permitAll)
//                .rememberMe(Customizer.withDefaults());
//
//
//        // 如果你希望禁用 CSRF 保护，可以取消注释以下行
//        // CSRF 保护的配置
//        http.csrf(csrf -> csrf
//                .requireCsrfProtectionMatcher(request -> {
//                    // 这里可以自定义需要 CSRF 保护的请求
//                    return request.getMethod().equals("POST");
//                })
//        );
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        managerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
//        return managerBuilder.build();
//    }
//
//}
