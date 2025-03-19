package com.litblc.shiro.service;

import com.litblc.shiro.entity.Users;

public interface IUserService {
    void registerUser(Users users);
    public Users findByUsername(String username);
}
