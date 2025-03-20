package com.litblc.shiro.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

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

        System.out.println("JwtUtils:getSignKey");

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成token
     * @param username
     * @return
     */
    public String generateToken(String username) {
        System.out.println("JwtUtils:generateToken");

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        System.out.println("JwtUtils:validateToken");

        try {
            Jwts.parser().setSigningKey(getSignKey()).build().parseSignedClaims(token);

            System.out.println("验证token没报错");

            return true;
        } catch (Exception e) {
            System.out.println("验证token异常");
            return false;
        }
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameFormToken (String token) {
        System.out.println("JwtUtils:getUsernameFormToken");

        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
