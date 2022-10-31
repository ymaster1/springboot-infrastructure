package cn.me.ppx.infrastructure.common.cache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author ym
 * @date 2022/10/31 10:22
 */
public class CacheTest {
    public static void main(String[] args) {
        Cache<String,String> cache = Caffeine.newBuilder().build();
    }
}
