package cn.me.ppx.infrastructure.redis.table

/**
 * @author  ym
 * @date  2022/11/7 18:15
 *
 */
interface TableRedis {
    fun set(table: String, key: String, value: String)

    /**
     * SETEX
     * 原子操作
     */
    fun set(table: String, key: String, value: String, expire: Long)

    /**
     * SETNX
     */
    fun setIfAbsent(table: String, key: String, value: String): Boolean
    fun setIfAbsent(table: String, key: String, value: String, expire: Long)
    fun setIfPresent(table: String, key: String, value: String)

    fun setIfPresent(table: String, key: String, value: String, expire: Long)

    fun get(table: String, key: String): String
}