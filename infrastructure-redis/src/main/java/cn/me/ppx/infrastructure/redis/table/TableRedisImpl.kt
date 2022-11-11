package cn.me.ppx.infrastructure.redis.table

import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

/**
 * @author  ym
 * @date  2022/11/7 18:15
 *
 */
class TableRedisImpl(private val redisTemplate: StringRedisTemplate) : TableRedis {
    fun set(table: String, key: String, value: String) {
        redisTemplate.opsForValue().set("$table:$key", value)
    }

    /**
     * SETEX
     * 原子操作
     */
    fun set(table: String, key: String, value: String, expire: Long) {
        redisTemplate.opsForValue().set("$table:$key", value, expire, TimeUnit.SECONDS)
    }

    /**
     * SETNX
     */
    fun setIfAbsent(table: String, key: String, value: String): Boolean {
        return redisTemplate.opsForValue().setIfAbsent("$table:$key", value) ?: false
    }

    fun setIfAbsent(table: String, key: String, value: String, expire: Long) {
        redisTemplate.opsForValue().setIfAbsent("$table:$key", value, expire, TimeUnit.SECONDS)
    }

    fun setIfPresent(table: String, key: String, value: String) {
        redisTemplate.opsForValue().setIfPresent("$table:$key", value)
    }

    fun setIfPresent(table: String, key: String, value: String, expire: Long) {
        redisTemplate.opsForValue().setIfPresent("$table:$key", value, expire, TimeUnit.SECONDS)
    }

    fun get(table: String, key: String): String {
        return redisTemplate.opsForValue().get("$table:$key") ?: ""
    }

}