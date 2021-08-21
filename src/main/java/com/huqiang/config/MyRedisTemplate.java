package com.huqiang.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/***************************************************************************************
 *功能介绍：
 *@date 2021/8/820:08
 ***************************************************************************************/
@Configuration
public class MyRedisTemplate {
    @Bean("myStringRedisTemplate")
    public StringRedisTemplate myStringRedisTemplate(RedisConnectionFactory fc){
        StringRedisTemplate myStringRedisTemplate = new StringRedisTemplate(fc);
        myStringRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        myStringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return myStringRedisTemplate;
    }
    @Bean
    @Primary
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
