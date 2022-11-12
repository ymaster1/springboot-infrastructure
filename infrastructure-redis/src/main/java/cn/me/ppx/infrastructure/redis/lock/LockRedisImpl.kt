package cn.me.ppx.infrastructure.redis.lock

import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.scripting.support.ResourceScriptSource

/**
 * @author  ym
 * @date  2022/11/7 18:16
 * 基于redis lua的分布式锁，释放的时候需要考虑是不是释放的别人的锁
 */
class LockRedisImpl(private val redisTemplate: StringRedisTemplate) : LockRedis {
    private val prefix = "LOCK_PREFIX"
    private val defaultExpire = 30L
    override fun lock(key: String): Boolean {
        return lock(key, defaultExpire)
    }

    override fun lock(key: String, expire: Long): Boolean {
        return lock(key, key, expire)
    }

    override fun lock(key: String, value: String, expire: Long): Boolean {
        val redisScript: DefaultRedisScript<Long> = DefaultRedisScript()
        redisScript.resultType = Long::class.java
        redisScript.setScriptSource(ResourceScriptSource(ClassPathResource("redis/lock.lua")))
        return redisTemplate.execute(
            redisScript,
            listOf("${prefix}:$key"),
            value, expire.toString()
        ) > 0L
    }


    override fun unLock(key: String): Boolean {
        return unLock(key, key)
    }

    override fun unLock(key: String, value: String): Boolean {
        val redisScript: DefaultRedisScript<Long> = DefaultRedisScript()
        redisScript.resultType = Long::class.java
        redisScript.setScriptSource(ResourceScriptSource(ClassPathResource("redis/unlock.lua")))
        return redisTemplate.execute(redisScript, listOf("${prefix}:$key"), value) > 0L
    }

}