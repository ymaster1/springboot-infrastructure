package cn.me.ppx.infrastructure.common.cache;


import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.CacheManager;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author ym
 * @date 2022/10/31 10:22
 * caffeine > redis >mysql
 */
public class CacheTest {
    // 同步加载
    public static void load() {
        LoadingCache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(100).build(new CacheLoader<String, String>() {
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

    // 手动填充，跟正常map一样
    public static void manualLoad(String key) {
        // 手动加载缓存
        Cache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100).build();
        // 需要手动put
        cache.put("ppx","ymaster1");
        // 多线程时只会有一个线程去更新，后续的线程会阻塞直到更新完成，
        String value = cache.get(key, (k) -> {
            return k + "v";
        });
    }
    public static void async(){
        AsyncCache<String, String> cache = Caffeine.newBuilder().buildAsync();

    }

    public static void asyncLoad(){
        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder().buildAsync(new AsyncCacheLoader<String, String>() {
            @Override
            public @NonNull CompletableFuture<String> asyncLoad(@NonNull String key, @NonNull Executor executor) {
                return null;
            }
        });
    }

    public static void main(String[] args) {
        LocalCache<String> stringLocalCache = LocalCacheFactory.defaultLocalCache(String.class);

    }
}
