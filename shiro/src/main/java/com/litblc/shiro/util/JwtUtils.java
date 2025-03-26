package com.litblc.shiro.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    private static final long EXPIRATION_MS = 86400000L * 30 * 6;       // 设置过期时间 6个月

    @Value("${jwt.secret}") // 从配置读取密钥
    private String secret;

    /**
     * 生成安全密钥
     * @return
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        log.info("JwtUtils:getSignKey");

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成token
     * @param username
     * @return
     */
    public String generateToken(String username, Long userId) {
        log.info("JwtUtils:generateToken");

        return Jwts.builder()
                .subject(username)       // 设置 JWT 的主题
                .issuer("shyZhen")       // 设置 JWT 的发行者，通常是你的应用或服务名称
                .claim("userId", userId) // 自定义声明id字段
                .issuedAt(new Date())    // 设置 JWT 的签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))  // 设置 JWT 的过期时间
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        log.info("JwtUtils:validateToken");

        try {
            Jwts.parser().setSigningKey(getSignKey()).build().parseSignedClaims(token);

            log.info("验证token没报错");

            return true;
        } catch (Exception e) {
            log.info("验证token异常");
            return false;
        }
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameFormToken (String token) {
        log.info("JwtUtils:getUsernameFormToken");

        Claims claims =  Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        log.info("从token中获取用户名和id-getUsernameFormToken:"+userId+"-"+username);
        return username;
    }

}
