package cn.me.ppx.infrastructure.common.cache

import cn.me.ppx.infrastructure.common.cache.caffine.CaffeineLoadingLocalCache
import cn.me.ppx.infrastructure.common.cache.caffine.CaffeineLocalCache

/**
 * @author  ym
 * @date  2022/11/11 15:21
 *
 */
class KotlinLocalCacheFactory {
    private fun KotlinLocalCacheFactory() {}

    companion object {
        inline fun <reified T> defaultLocalCache(): CaffeineLocalCache<T> {
            return CaffeineLocalCache()
        }

        inline fun <reified T> loadingLocalCache(crossinline loader: (String) -> T): CaffeineLoadingLocalCache<T> {
            return CaffeineLoadingLocalCache { loader.invoke(it) }
        }
    }
}