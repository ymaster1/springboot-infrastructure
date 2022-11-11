package cn.me.ppx.infrastructure.common.cache;

import cn.me.ppx.infrastructure.common.cache.caffine.CaffeineLocalCache;

/**
 * @author ym
 * @date 2022/11/11 11:04
 */
public class LocalCacheFactory {
    private LocalCacheFactory() {
    }

    /**
     * 默认使用caffeine
     * 静态方法获取类的泛型
     * kotlin 反射 不需要加这个参数
     *
     * @return
     */
    public static <T> LocalCache<T> defaultLocalCache(Class<T> clazz) {
        return new CaffeineLocalCache<>();
    }

    /**
     * 值是String的缓存
     *
     * @return
     */
    public static LocalCache<String> defaultLocalCache() {
        return new CaffeineLocalCache<>();
    }
}
