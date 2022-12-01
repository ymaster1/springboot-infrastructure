package cn.me.ppx.infrastructure.websocket.annotation


/**
 * @author nanfeng
 * @date 2020/7/20 10:13 上午
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class PathVariable(
        val value: String
)
