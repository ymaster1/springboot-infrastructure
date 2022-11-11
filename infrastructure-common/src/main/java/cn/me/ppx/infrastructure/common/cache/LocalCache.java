package cn.me.ppx.infrastructure.common.cache;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

/**
 * @author ym
 * @date 2022/11/11 10:03
 * 本地内存缓存,默认使用caffeine实现
 * 可以自己用别的实现
 * T 缓存值的类型
 */
public interface LocalCache<T> {
    public void put(@NonNull String key, @NonNull T value);

    public void putAll(@NonNull Map<? extends @NonNull String, ? extends @NonNull T> map);

    public T get(@NonNull String key);

    public T getIfPresent(@NonNull String key);

    void clean(@NonNull String key);


    void cleanAll(@NonNull Iterable<@NonNull String> keys);


    void cleanAll();
}
