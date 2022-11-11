package cn.me.ppx.infrastructure.redis.cache

/**
 * @author  ym
 * @date  2022/11/7 18:19
 *
 */
interface CacheRedis {
    fun put(key: String, value: String)

    fun get(key: String): String

    /**
     * @param value 当缓存不存在时更怎么更新缓存
     */
    fun get(key: String, value: (String) -> String): String
}