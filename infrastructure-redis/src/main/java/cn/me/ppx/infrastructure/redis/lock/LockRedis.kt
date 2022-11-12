package cn.me.ppx.infrastructure.redis.lock

/**
 * @author  ym
 * @date  2022/11/7 18:16
 *
 */
interface LockRedis {
    fun lock(key: String): Boolean
    fun lock(key: String, expire: Long): Boolean

    fun lock(key: String, value: String, expire: Long): Boolean


    fun unLock(key: String): Boolean

    fun unLock(key: String, value: String): Boolean
}