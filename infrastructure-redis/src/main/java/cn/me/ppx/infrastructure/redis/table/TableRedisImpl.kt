package cn.me.ppx.infrastructure.redis.table

import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.scripting.support.ResourceScriptSource
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

    override fun setIfAbsent(table: String, key: String, value: String, expire: Long): Boolean {
        return redisTemplate.opsForValue().setIfAbsent("$table:$key", value, expire, TimeUnit.SECONDS) ?: false
    }

    override fun setIfPresent(table: String, key: String, value: String): Boolean {
        return redisTemplate.opsForValue().setIfPresent("$table:$key", value) ?: false
    }

    override fun setIfPresent(table: String, key: String, value: String, expire: Long): Boolean {
        return redisTemplate.opsForValue().setIfPresent("$table:$key", value, expire, TimeUnit.SECONDS) ?: false
    }

    override fun get(table: String, key: String): String? {
        return redisTemplate.opsForValue().get("$table:$key")
    }

    override fun getSequenceNo(key: String, value: String): Long {
        val redisScript: DefaultRedisScript<Long> = DefaultRedisScript()
        redisScript.resultType = Long::class.java
        redisScript.setScriptSource(ResourceScriptSource(ClassPathResource("redis/getSequenceNo.lua")))
        return redisTemplate.execute(
            redisScript,
            listOf(key),
            value
        )
    }
}