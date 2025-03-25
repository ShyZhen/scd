package com.litblc.shiro.security;

import com.litblc.shiro.Common.Exception.ServiceException;
import com.litblc.shiro.dto.LoginRequest;
import com.litblc.shiro.dto.RegisterRequest;
import com.litblc.shiro.entity.Users;
import com.litblc.shiro.mapper.UserMapper;
import com.litblc.shiro.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
            throw new ServiceException("用户名已经存在:"+request.getName());
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
            throw new ServiceException("注册失败,请重试");
        }
    }

    /**
     * 登录，生成token
     * @param request
     * @return
     */
    public String login(LoginRequest request) {

        // 创建认证令牌
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getName(),
                request.getPassword()
        );

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 获取用户详情（包含用户ID）
            CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
            Long userId = userDetail.getUserId();

            System.out.println("登录获取userDetail中的userId:"+userId);
            return  jwtUtils.generateToken(userDetail.getUsername(), userId);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }


}
