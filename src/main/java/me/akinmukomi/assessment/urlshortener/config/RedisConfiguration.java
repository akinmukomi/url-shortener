//package me.akinmukomi.assessment.urlshortener.config;
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.cache.CacheProperties;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//import java.time.Duration;
//import java.util.LinkedHashSet;
//import java.util.List;
//
//@Configuration
//public class RedisConfiguration extends CachingConfigurerSupport {
//
//    @Value("${spring.data.redis.ip}")
//    private String redisServerIp;
//
//    @Value("${spring.data.redis.port}")
//    private int redisServerPort;
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory()
//    {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(redisServerIp);
//        jedisConnectionFactory.setPort(redisServerPort);
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate()
//    {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        redisTemplate.setExposeConnection(true);
//        return redisTemplate;
//    }
//}
