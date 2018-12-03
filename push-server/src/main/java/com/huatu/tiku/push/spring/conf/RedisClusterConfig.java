package com.huatu.tiku.push.spring.conf;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.huatu.common.spring.serializer.StringRedisKeySerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.huatu.common.consts.ApolloConfigConsts.NAMESPACE_TIKU_REDIS;

/**
 * Redis 配置信息
 *
 * @author hanchao
 * @date 2017/9/4 14:42
 */
@EnableApolloConfig(NAMESPACE_TIKU_REDIS)
@Configuration
public class RedisClusterConfig {
    @Value("${spring.application.name:unknown}")
    private String applicationName;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    @Autowired
    private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer;

    /**
     * 此处 key 序列化定义 声明了命令空间
     */
    @Bean
    public StringRedisKeySerializer stringRedisKeySerializer() {
        return new StringRedisKeySerializer(applicationName);
    }

    /**
     * 不带有 命名空间的 key 序列化
     */
    @Bean(name = "stringRedisKeySerializerWithoutServerName")
    public StringRedisSerializer stringRedisKeySerializerWithoutServerName() {
        return new StringRedisSerializer();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }

    /**
     * 使用官方的，防止踩坑
     */
    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisTemplate redisTemplate(StringRedisKeySerializer stringRedisKeySerializer) {
        return redisTemplateFactory(stringRedisKeySerializer);
    }

    @Bean(name = "redisTemplateWithoutServerName")
    public RedisTemplate redisTemplateWithoutServerName(@Qualifier("stringRedisKeySerializerWithoutServerName") StringRedisSerializer stringRedisSerializer) {
        return redisTemplateFactory(stringRedisSerializer);
    }

    private RedisTemplate redisTemplateFactory(StringRedisSerializer stringRedisSerializer) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
