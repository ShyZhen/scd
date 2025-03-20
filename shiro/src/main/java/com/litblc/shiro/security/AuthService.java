package com.litblc.shiro.security;

import com.litblc.shiro.dto.LoginRequest;
import com.litblc.shiro.dto.RegisterRequest;
import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.UserMapper;
import com.litblc.shiro.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor  // final类型成员自动依赖注入，省去写构造函数注入
public class AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * 用户注册
     * @param request
     * @return
     */
    public Users register(RegisterRequest request) {
        if (userMapper.existsByUsername(request.getName())) {
            throw new RuntimeException("用户名已经存在:"+request.getName());
        }

        String uuid = "xxx-111-222";
        Users users = new Users();
        users.setUuid(uuid)
                .setName(request.getName())
                .setEmail(request.getEmail())
                .setMobile(request.getMobile())
                .setGender(request.getGender())
                .setPassword(passwordEncoder.encode(request.getPassword()));

        if (userMapper.insert(users) > 0) {
            return users;
        } else {
            throw new RuntimeException("注册失败,请重试");
        }
    }

    /**
     * 登录，生成token
     * @param request
     * @return
     */
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );

        return  jwtUtils.generateToken(request.getName());
    }


}
