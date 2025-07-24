package sh.roadmap.ecommerce.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for Redis cache.
 * Defines beans for RedisTemplate and Jackson2JsonRedisSerializer to customize Redis operations.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Configuration: Marks this class as a source of bean definitions for the Spring container.</li>
 *   <li>@Resource: Injects the RedisConnectionFactory bean for establishing Redis connections.</li>
 * </ul>
 */
@Configuration
public class RedisCacheConfiguration {

    /**
     * Redis connection factory for creating Redis connections.
     * Injected by Spring using the @Resource annotation.
     */
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Configures a RedisTemplate bean for Redis operations.
     * Sets custom serializers for keys, values, hash keys, and hash values.
     *
     * @return A configured RedisTemplate instance.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        return redisTemplate;
    }

    /**
     * Configures a Jackson2JsonRedisSerializer bean for serializing objects to JSON.
     * Customizes the ObjectMapper to allow all fields to be visible and enable default typing.
     *
     * @return A configured Jackson2JsonRedisSerializer instance.
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.serialize(objectMapper);

        return jackson2JsonRedisSerializer;
    }
}
