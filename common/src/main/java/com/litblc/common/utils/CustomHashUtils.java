package com.litblc.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CustomHashUtils {

    // md5盐值长度
    private static final int saltLength = 8;
    // md5盐值隐藏位置
    private static final int saltIndex = 7;

    /**
     * 原生 md5
     * @param input
     * @return
     */
    public static String md5(String input) {
        try {
            // 1. 获取md5摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2. 计算哈希值，返回字节数组
            byte[] hashBytes = md.digest(input.getBytes());

            // 3. 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);  // 将字节转换为2位十六进制字符串,并确保字节转换为无符号整数
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * 加盐 md5
     * @param input
     * @return
     */
    public static String md5WithSalt(String input) {
        // 生成随机8位盐值
        String salt = UUID.randomUUID().toString().substring(0, saltLength);

        // 带加密字符串拼接盐值
        String saltedInput = input + salt;

        // md5计算
        String hashedString = md5(saltedInput);

        // 将盐值隐藏在哈希值的特定位置（例如第 8-15 位）
        return hashedString.substring(0, saltIndex) + salt + hashedString.substring(saltIndex);
    }

    /**
     * 手动传入盐值 md5, 用于 verify
     * @param input
     * @param salt
     * @return
     */
    public static String md5WithSalt(String input, String salt) {
        // 带加密字符串拼接盐值
        String saltedInput = input + salt;

        // md5计算
        String hashedString = md5(saltedInput);

        // 将盐值隐藏在哈希值的特定位置,方便验证（例如第 7-15 位）
        return hashedString.substring(0, saltIndex) + salt + hashedString.substring(saltIndex);
    }

    /**
     * 验证加盐的md5
     * @param input
     * @param hashedString
     * @return
     */
    public static boolean verifySaltedMd5(String input, String hashedString) {
        // 提取盐值，盐值长度=saltLength，隐藏在7-15位(与md5WithSalt对应)
        String salt = hashedString.substring(saltIndex, saltIndex + saltLength);

        String expectedHash = md5WithSalt(input, salt);
        return hashedString.equals(expectedHash);
    }


}
