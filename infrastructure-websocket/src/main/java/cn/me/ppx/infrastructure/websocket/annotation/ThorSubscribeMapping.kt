package cn.me.ppx.infrastructure.websocket.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ThorSubscribeMapping (
    vararg val value: String
)