package com.litblc.fmock.moduleA.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 使redis手动操作保存的数据支持<string,object>，并使保存的数据为json字符串。否则是二进制
 *
 * @Author zhenhuaixiu
 * @Date 2023/11/6 10:51
 * @Version 1.0
 */
@Configuration
@EnableCaching  // @EnableCaching为开启缓存，可以放在任何一个能被自动加载的地方
public class RedisTemplateConfig extends RedisTemplate<String, Object> {

    public RedisTemplateConfig(RedisConnectionFactory redisConnectionFactory) {

        // 构造函数注入 RedisConnectionFactory，设置到父类
        super.setConnectionFactory(redisConnectionFactory);

        // 使用 Jackson 提供的通用 Serializer
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        serializer.configure(mapper -> {
            // 如果涉及到对 java.time 类型的序列化，反序列化那么需要注册 JavaTimeModule
            mapper.registerModule(new JavaTimeModule());
        });

        // String 类型的 key/value 序列化
        super.setKeySerializer(StringRedisSerializer.UTF_8);
        super.setValueSerializer(serializer);

        // Hash 类型的 key/value 序列化
        super.setHashKeySerializer(StringRedisSerializer.UTF_8);
        super.setHashValueSerializer(serializer);
    }
}