package cn.me.ppx.infrastructure.websocket.thor.handler

import me.jinuo.imf.thor.annotation.ThorStompController
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
                        ThorStompController::class.java)
                ) {
                    val controller =
                        AnnotatedElementUtils.findMergedAnnotation(beanType, ThorStompController::class.java)!!
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
