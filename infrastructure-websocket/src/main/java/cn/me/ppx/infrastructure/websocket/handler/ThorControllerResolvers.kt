package cn.me.ppx.infrastructure.websocket.handler

import cn.me.ppx.infrastructure.websocket.annotation.StompController
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

/**
 * 寻找并注册 Controller
 */
class ThorControllerResolvers(
    private val context: ApplicationContext,
    private val interceptors: List<CustomPathHandshakeInterceptor>?
) {

    private val TARGET_NAME_PREFIX = "scopedTarget."

    fun resolveThorController(registry: StompEndpointRegistry) {
        for (beanName in context.getBeanNamesForType(Any::class.java)) {
            if (!beanName.startsWith(TARGET_NAME_PREFIX)) {
                var beanType: Class<*>? = null
                try {
                    beanType = context.getType(beanName)
                } catch (ex: Throwable) {
                    // An unresolvable bean type, probably from a lazy bean - let's ignore it.
                }

                if (beanType != null && AnnotatedElementUtils.hasAnnotation(beanType,
                        StompController::class.java)
                ) {
                    val controller =
                        AnnotatedElementUtils.findMergedAnnotation(beanType, StompController::class.java)!!
                    registry.addEndpoint(controller.value)
                        .setAllowedOrigins("*")
                        .run {
                            interceptors?.filter {
                                it.supportPath().contains(controller.value)
                            }?.forEach {
                                this.addInterceptors(it)
                            }
                        }
                }
            }
        }
    }

}
