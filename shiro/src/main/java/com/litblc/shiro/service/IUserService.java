package com.litblc.shiro.service;

import com.litblc.shiro.entity.Users;

public interface IUserService {
    public Users findByUsername(String username);
}
