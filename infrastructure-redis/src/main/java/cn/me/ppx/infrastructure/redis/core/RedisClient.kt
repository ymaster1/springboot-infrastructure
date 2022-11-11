package cn.me.ppx.infrastructure.redis.core

import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.scripting.support.ResourceScriptSource

/**
 * @author  ym
 * @date  2022/11/8 11:00
 *
 */
class RedisClient(private val stringRedisTemplate: StringRedisTemplate) {
    /**
     * 获取序列号
     */
    fun getSequenceNo(key: String, value: String): Long {
        val redisScript: DefaultRedisScript<Long> = DefaultRedisScript()
        redisScript.resultType = Long::class.java
        redisScript.setScriptSource(ResourceScriptSource(ClassPathResource("redis/getSequenceNo.lua")))
        return stringRedisTemplate.execute(
            redisScript,
            listOf(key),
            value
        )
    }
}