package cn.me.ppx.infrastructure.common.cache

import cn.me.ppx.infrastructure.common.cache.caffine.CaffeineLocalCache

/**
 * @author  ym
 * @date  2022/11/11 15:21
 *
 */
class KotlinLocalCacheFactory {
    inline fun <reified T> defaultLocalCache(): LocalCache<T> {
        return CaffeineLocalCache()
    }

    fun test(){
        // 这里直接就知道泛型时String
        val cache = defaultLocalCache<String>()
    }
}