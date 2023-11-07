package com.coderman.bizedu.config;

import com.coderman.redis.config.BaseRedisConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhangyukang
 * @date ：2023/10/17 11:33
 */
@Component(value = "bizEduRedisConfig")
@Configuration
public class RedisConfig extends BaseRedisConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    @Primary
    public RedisProperties bizEduRedisProperties(){
        return new RedisProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.jedis.pool")
    public JedisPoolConfig  bizEduJedisPoolConfig(){
        return new JedisPoolConfig();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier(value = "bizEduRedisProperties") RedisProperties properties,@Qualifier(value = "bizEduJedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        return createJedisConnectionFactory(properties, jedisPoolConfig);
    }


    @Bean
    public StringRedisTemplate redisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory){
        return createStringRedisTemplate(jedisConnectionFactory);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(@Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory){
        return createMessageListenerContainer(jedisConnectionFactory);
    }

}
