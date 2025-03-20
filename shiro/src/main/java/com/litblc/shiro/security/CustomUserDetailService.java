package com.litblc.shiro.security;

import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 实现 security 的 UserDetailsService 方法
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> usersOptional = Optional.ofNullable(userMapper.findByUsername(username));
        Users users = usersOptional.orElseThrow(() -> new UsernameNotFoundException("用户不存在哦:"+username));

        System.out.println("security:CustomUserDetailService:loadUserByUsername: 实现security的UserDetailsService");
        // System.out.println(usersOptional);

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + "USER")  // 实体中有 role 字段就把USER改成 user.getRole()
        );

        return new org.springframework.security.core.userdetails.User(
                users.getName(),
                users.getPassword(),
                authorities
        );
    }
}
