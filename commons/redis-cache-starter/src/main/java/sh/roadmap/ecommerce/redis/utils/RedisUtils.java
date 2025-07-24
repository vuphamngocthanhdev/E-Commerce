package sh.roadmap.ecommerce.redis.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Utility class for interacting with Redis.
 * Provides methods for setting, getting, expiring, and deleting keys in Redis.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Component: Marks this class as a Spring-managed component.</li>
 *   <li>@Resource: Injects the RedisTemplate bean for Redis operations.</li>
 * </ul>
 */
@Component
public class RedisUtils {

    /**
     * RedisTemplate for performing Redis operations.
     * Injected by Spring using the @Resource annotation.
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Default expiration time for keys in seconds (24 hours).
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;

    /**
     * Expiration time for keys in seconds (1 hour).
     */
    public final static long HOUR_ONE_EXPIRE = (long) 60 * 60;

    /**
     * Expiration time for keys in seconds (6 hours).
     */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;

    /**
     * Constant indicating no expiration for a key.
     */
    public final static long NOT_EXPIRE = -1L;

    /**
     * Sets a key-value pair in Redis with a specified expiration time.
     *
     * @param key    The key to set.
     * @param value  The value to associate with the key.
     * @param expire The expiration time in seconds. Use {@link #NOT_EXPIRE} for no expiration.
     */
    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * Sets the expiration time for a key in Redis.
     *
     * @param key    The key to set the expiration for.
     * @param expire The expiration time in seconds.
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * Sets a key-value pair in Redis with the default expiration time.
     *
     * @param key   The key to set.
     * @param value The value to associate with the key.
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * Retrieves the value associated with a key from Redis and optionally resets its expiration time.
     *
     * @param key    The key to retrieve the value for.
     * @param expire The expiration time to reset in seconds. Use {@link #NOT_EXPIRE} to skip resetting expiration.
     * @return The value associated with the key, or null if the key does not exist.
     */
    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null && expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    /**
     * Retrieves the value associated with a key from Redis without resetting its expiration time.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key, or null if the key does not exist.
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * Deletes a key from Redis.
     *
     * @param key The key to delete.
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
