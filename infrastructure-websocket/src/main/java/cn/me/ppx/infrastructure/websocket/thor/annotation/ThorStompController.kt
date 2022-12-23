package me.jinuo.imf.thor.annotation

import org.springframework.stereotype.Component


@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ThorStompController(
        val value: String
)
