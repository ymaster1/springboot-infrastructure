package me.jinuo.imf.thor.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ThorSubscribeMapping (
    vararg val value: String
)