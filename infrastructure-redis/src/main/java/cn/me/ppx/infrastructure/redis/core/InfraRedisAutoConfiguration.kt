package cn.me.ppx.infrastructure.redis.core

import cn.me.ppx.infrastructure.common.cache.KotlinLocalCacheFactory
import cn.me.ppx.infrastructure.redis.cache.CacheRedis
import cn.me.ppx.infrastructure.redis.cache.CacheRedisImpl
import cn.me.ppx.infrastructure.redis.table.TableRedis
import cn.me.ppx.infrastructure.redis.table.TableRedisImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate

/**
 * @author  ym
 * @date  2022/11/8 11:01
 *
 */
@Configuration
@EnableConfigurationProperties(InfrastructureRedisConfig::class)
class InfraRedisAutoConfiguration {
    @Autowired
    private lateinit var infrastructureRedisConfig: InfrastructureRedisConfig

    @Bean
    fun tableRedis(stringRedisTemplate: StringRedisTemplate): TableRedis = TableRedisImpl(stringRedisTemplate)

    @Bean
    fun cacheRedis(stringRedisTemplate: StringRedisTemplate): CacheRedis =
        CacheRedisImpl(
            KotlinLocalCacheFactory.defaultLocalCache(),
            tableRedis(stringRedisTemplate),
            infrastructureRedisConfig.enableLocal
        )

    @Bean
    fun redisClient(stringRedisTemplate: StringRedisTemplate): RedisClient = RedisClient(stringRedisTemplate)

}