package br.com.davi.spring_boot_first.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;


// Swapping Java's default byte serialization for JSON so Redis stores the objects correctly
@Configuration
@EnableCaching
public class RedisConfig {


    @Bean
    RedisCacheConfiguration cacheConfiguration() {

        // generic save the type of object to deserialize correctly
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();


        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                );
    }

}