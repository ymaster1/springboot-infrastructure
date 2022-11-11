package cn.me.ppx.infrastructure.common.cache.caffine;

import cn.me.ppx.infrastructure.common.cache.LocalCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author ym
 * @date 2022/11/11 11:06
 * 代理给caffeine
 */
public class CaffeineLocalCache<T> implements LocalCache<T> {

    private final Cache<String, T> cache;

    public CaffeineLocalCache() {
        cache = Caffeine.newBuilder().build();
    }

    @Override
    public T get(@NotNull String key) {
        return null;
    }

    @Override
    public T getIfPresent(@NonNull String key) {
        return cache.getIfPresent(key);
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

    /**
     * 返回旧值
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public void put(@NotNull String key, @NotNull T value) {
        cache.put(key, value);
    }

    @Override
    public void putAll(@NonNull Map<? extends @NonNull String, ? extends @NonNull T> map) {

    }
}