package cn.me.ppx.infrastructure.redis.core

import cn.me.ppx.infrastructure.common.cache.KotlinLocalCacheFactory
import cn.me.ppx.infrastructure.redis.cache.CacheRedis
import cn.me.ppx.infrastructure.redis.cache.CacheRedisImpl
import cn.me.ppx.infrastructure.redis.lock.LockRedis
import cn.me.ppx.infrastructure.redis.lock.LockRedisImpl
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
 * @ConditionalOnProperty
 * @AutoConfigureBefore
 * SpringBootCondition
 * 要控制链接信息这些就再重新定义 RedisConnectionFactory StringRedisTemplate
 */
@Configuration
@EnableConfigurationProperties(InfrastructureRedisConfig::class)
class InfraRedisAutoConfiguration {

    @Bean
    fun tableRedis(stringRedisTemplate: StringRedisTemplate): TableRedis = TableRedisImpl(stringRedisTemplate)

    @Bean
    fun cacheRedis(stringRedisTemplate: StringRedisTemplate,infrastructureRedisConfig: InfrastructureRedisConfig): CacheRedis =
        CacheRedisImpl(
            KotlinLocalCacheFactory.defaultLocalCache(),
            tableRedis(stringRedisTemplate),
            infrastructureRedisConfig
        )

    @Bean
    fun lockRedis(stringRedisTemplate: StringRedisTemplate): LockRedis = LockRedisImpl(stringRedisTemplate)
}