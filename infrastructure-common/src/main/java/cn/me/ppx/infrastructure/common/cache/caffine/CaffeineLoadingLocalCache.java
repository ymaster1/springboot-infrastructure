package cn.me.ppx.infrastructure.common.cache.caffine;

import cn.me.ppx.infrastructure.common.cache.LocalCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ym
 * @date 2022/11/11 15:38
 */
public class CaffeineLoadingLocalCache<T> implements LocalCache<T> {
    private final LoadingCache<String, T> cache;

    public CaffeineLoadingLocalCache(CacheLoader<String, T> cacheLoader) {
        cache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(100).build(cacheLoader);
    }

    @Override
    public void put(@NonNull String key, @NonNull T value) {

    }

    @Override
    public void putAll(@NonNull Map<? extends @NonNull String, ? extends @NonNull T> map) {

    }

    @Override
    public T get(@NonNull String key) {
        return cache.get(key);
    }

    @Override
    public T getIfPresent(@NonNull String key) {
        return null;
    }

    @Override
    public void clean(@NonNull String key) {

    }

    @Override
    public void cleanAll(@NonNull Iterable<@NonNull String> keys) {

    }

    @Override
    public void cleanAll() {

    }
}
