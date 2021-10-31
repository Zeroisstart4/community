package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author zhou
 * @create 2021-3-29 20:34
 */
// Redis 模板配置类
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 Redis 模板
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 为 Redis 模板设置连接工厂
        template.setConnectionFactory(factory);
        // 设置 key 的序列化方式,String
        template.setKeySerializer(RedisSerializer.string());
        // 设置 value 的序列化方式,Json
        template.setValueSerializer(RedisSerializer.json());
        // 设置 hash 的 key 的序列化方式,String
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置 hash 的 value 的序列化方式,Json
        template.setHashValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();

        return template;

    }
}
