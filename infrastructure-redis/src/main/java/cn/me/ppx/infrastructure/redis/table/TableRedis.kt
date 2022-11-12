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

    /**
     * SET NX
     */
    fun setIfAbsent(table: String, key: String, value: String, expire: Long):Boolean
    fun setIfPresent(table: String, key: String, value: String):Boolean

    /**
     * SET EX
     */
    fun setIfPresent(table: String, key: String, value: String, expire: Long):Boolean

    /**
     * @return 不存在时返回 null
     *
     */
    fun get(table: String, key: String): String?

    fun getSequenceNo(key: String, value: String):Long
}