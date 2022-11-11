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
    private final LoadingCache<String, String> cache;

    public CaffeineLoadingLocalCache() {
        cache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(100).build(new CacheLoader<String, String>() {
            /**
             * 当缓存不存在/缓存已过期时，若调用get()方法，则会自动调用CacheLoader.load()方法加载最新值
             * 当两个线程同时调用get()，则后一线程将被阻塞，直至前一线程更新缓存完成。
             * @param key the non-null key whose value should be loaded
             * @return
             * @throws Exception
             */
            @Override
            public @Nullable String load(@NonNull String key) throws Exception {
                return null;
            }

            @Override
            public @NonNull Map<@NonNull String, @NonNull String> loadAll(@NonNull Iterable<? extends @NonNull String> keys) throws Exception {
                return CacheLoader.super.loadAll(keys);
            }
        });
    }

    @Override
    public void put(@NonNull String key, @NonNull T value) {

    }

    @Override
    public void putAll(@NonNull Map<? extends @NonNull String, ? extends @NonNull T> map) {

    }

    @Override
    public T get(@NonNull String key) {
        return null;
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
