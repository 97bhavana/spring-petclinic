package org.springframework.samples.petclinic.redis;

import java.time.Duration;

//import org.ehcache.expiry.Duration;

//import javax.cache.expiry.Duration;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableJpaRepositories(basePackages = "org.springframework.samples.petclinic")
@EnableCaching
@ImportAutoConfiguration(classes = { 
		  CacheAutoConfiguration.class, 
		  RedisAutoConfiguration.class 
		})

public class RedisJpaConfig {
	
	/*@Bean
	public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
		return cm -> {
			cm.createCache("vets", cacheConfiguration());
		};
	}*/
	
	@Bean
	public RedisCacheConfiguration redisCacheConfiguration() {
	    return RedisCacheConfiguration.defaultCacheConfig()
	      .entryTtl(Duration.ofMinutes(60))
	      .disableCachingNullValues()
	      .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
	
	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
	    return (builder) -> builder
	      .withCacheConfiguration("ownerpetlist",
	        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));
	      
	}


}
