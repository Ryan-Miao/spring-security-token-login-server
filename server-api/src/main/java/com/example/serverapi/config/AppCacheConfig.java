package com.example.serverapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ryan Miao
 */
@Configuration
public class AppCacheConfig extends CachingConfigurerSupport {


    @Bean
    public RedissonSpringCacheManager cacheManager(RedissonClient redissonClient, ObjectMapper objectMapper)
        throws IOException {
        return new RedissonSpringCacheManager(redissonClient, "classpath:/cache-config.yml",
            new JsonJacksonCodec(objectMapper));
    }

}
