package me.jinuo.imf.thor.annotation


import me.jinuo.imf.thor.ThorMessageBrokerConfiguration
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@MustBeDocumented
@Import(ThorMessageBrokerConfiguration::class)
annotation class EnableThorWebSocket

