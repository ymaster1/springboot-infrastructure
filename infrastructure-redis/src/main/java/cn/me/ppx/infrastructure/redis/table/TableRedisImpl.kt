package cn.me.ppx.infrastructure.redis.table

import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

/**
 * @author  ym
 * @date  2022/11/7 18:15
 *
 */
class TableRedisImpl(private val redisTemplate: StringRedisTemplate) : TableRedis {
    override fun set(table: String, key: String, value: String) {
        redisTemplate.opsForValue().set("$table:$key", value)
    }

    /**
     * SETEX
     * 原子操作
     */
    override fun set(table: String, key: String, value: String, expire: Long) {
        redisTemplate.opsForValue().set("$table:$key", value, expire, TimeUnit.SECONDS)
    }

    /**
     * SETNX
     */
    override fun setIfAbsent(table: String, key: String, value: String): Boolean {
        return redisTemplate.opsForValue().setIfAbsent("$table:$key", value) ?: false
    }

    override fun setIfAbsent(table: String, key: String, value: String, expire: Long) {
        redisTemplate.opsForValue().setIfAbsent("$table:$key", value, expire, TimeUnit.SECONDS)
    }

    override fun setIfPresent(table: String, key: String, value: String) {
        redisTemplate.opsForValue().setIfPresent("$table:$key", value)
    }

    override fun setIfPresent(table: String, key: String, value: String, expire: Long) {
        redisTemplate.opsForValue().setIfPresent("$table:$key", value, expire, TimeUnit.SECONDS)
    }

    override fun get(table: String, key: String): String {
        return redisTemplate.opsForValue().get("$table:$key") ?: ""
    }

}