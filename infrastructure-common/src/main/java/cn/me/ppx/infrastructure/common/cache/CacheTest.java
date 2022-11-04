package cn.me.ppx.infrastructure.common.cache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author ym
 * @date 2022/10/31 10:22
 */
public class CacheTest {
    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100).build();
        String key = "ppx";
        System.out.println(cache.getIfPresent(key));

        // 也可以使用 get 方法获取值，该方法将一个参数为 key 的 Function 作为参数传入。如果缓存中不存在该 key
        // 则该函数将用于提供默认值，该值在计算后插入缓存中：
        System.out.println(cache.get(key, s -> {
            System.out.println(s);
            return "nill";
        }));

    }
}
