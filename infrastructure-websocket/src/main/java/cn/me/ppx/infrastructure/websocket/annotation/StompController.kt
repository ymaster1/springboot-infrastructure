package cn.me.ppx.infrastructure.websocket.annotation

import org.springframework.stereotype.Component


@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class StompController(
        val value: String
)
