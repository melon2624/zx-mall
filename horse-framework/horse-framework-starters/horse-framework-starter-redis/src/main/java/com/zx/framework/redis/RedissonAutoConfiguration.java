package com.zx.framework.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 *
 * @author luozhan
 */
@Configuration(proxyBeanMethods = false)
public class RedissonAutoConfiguration {

    @Bean
    public RedissonClient redissonClient(@Value("${spring.redis.host}") String host,
                                         @Value("${spring.redis.port}") String port,
                                         @Value("${spring.redis.password: }") String password,
                                         @Value("${spring.redis.database:0}") int database
                                         ) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        config.useSingleServer().setConnectionMinimumIdleSize(1);
        config.useSingleServer().setDatabase(database);
        return Redisson.create(config);
    }

}
