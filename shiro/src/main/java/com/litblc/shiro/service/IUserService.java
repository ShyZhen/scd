package com.litblc.shiro.service;

import com.litblc.shiro.entity.Users;

public interface IUserService {
    Users findByUsername(String username);
}
