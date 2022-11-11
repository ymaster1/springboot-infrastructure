package cn.me.ppx.infrastructure.redis.core

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotNull

/**
 * @author  ym
 * @date  2022/11/11 17:05
 *
 */
@ConfigurationProperties("infra.redis")
@Validated
@ConstructorBinding
data class InfrastructureRedisConfig(
    @field: NotNull
    var enableLocal: Boolean = true,

)