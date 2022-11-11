package cn.me.ppx.infrastructure.redis.cache

import cn.me.ppx.infrastructure.common.cache.LocalCache
import cn.me.ppx.infrastructure.redis.core.InfrastructureRedisConfig
import cn.me.ppx.infrastructure.redis.table.TableRedis

/**
 * @author  ym
 * @date  2022/11/7 18:19
 * @param config 缓存配置
 */
class CacheRedisImpl(
    private val localCache: LocalCache<String>,
    private val tableRedis: TableRedis,
    private val config: InfrastructureRedisConfig
) : CacheRedis {

    override fun put(key: String, value: String) {
        if (config.enableLocal) {
            localCache.put(key, value)
        }
        tableRedis.set(key, key, value)
    }

    override fun get(key: String): String {
        TODO("Not yet implemented")
    }

    override fun get(key: String, value: (String) -> String): String {
        return value.invoke(key)
    }
}