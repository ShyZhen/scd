package com.litblc.shiro.service.impl;

import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.UserMapper;
import com.litblc.shiro.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 控制器应该注入接口层，而不是接口实现impl，这样更灵活，可以随意改变service。
// spring会自动扫描实现了接口的impl注入进来；当有多个实现同一个接口的impl，需要定义@Primary确定主要的，或者使用别名指定
@Primary
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    //PasswordEncoder DelegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public void registerUser(Users users) {
        if (userMapper.existsByUsername(users.getName())) {
            log.error("This is an error log message");
            throw new RuntimeException("用户名已经存在了");
        }

        log.info("两种加密方式");
        users.setPassword(users.getPassword());
        //users.setPassword(passwordEncoder.encode(users.getPassword()));
        //users.setPassword(DelegatingPasswordEncoder.encode(users.getPassword()));

        userMapper.insert(users);
    }

    public Users findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
