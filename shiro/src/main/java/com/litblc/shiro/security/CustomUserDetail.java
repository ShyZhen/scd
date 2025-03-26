package com.litblc.shiro.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetail implements UserDetails {
    private final Long userId;  // 新增用户ID字段
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    // 下面为必须实现的方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // 其他账户状态方法（根据需求实现）
    @Override
    public boolean isAccountNonExpired() {  // 检查账户是否 没过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  // 检查账户是否 没有被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  // 检查凭据（密码）是否 没过期
        return true;
    }

    @Override
    public boolean isEnabled() {  // 检查账户是否启用
        return true;
    }

    // 新增获取用户ID的方法
    public Long getUserId() {
        return userId;
    }
}
