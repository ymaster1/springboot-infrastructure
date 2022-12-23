package cn.me.ppx.infrastructure.websocket.thor.annotation


import cn.me.ppx.infrastructure.websocket.thor.ThorMessageBrokerConfiguration
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@MustBeDocumented
@Import(ThorMessageBrokerConfiguration::class)
annotation class EnableThorWebSocket

