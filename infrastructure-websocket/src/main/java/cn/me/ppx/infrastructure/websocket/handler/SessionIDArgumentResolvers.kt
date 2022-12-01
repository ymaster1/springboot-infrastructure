package cn.me.ppx.infrastructure.websocket.handler

import cn.me.ppx.infrastructure.websocket.annotation.SessionId
import org.springframework.core.MethodParameter
import org.springframework.messaging.Message
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.messaging.simp.SimpMessageHeaderAccessor

/**
 * 提供 SessionId 注解的支持
 */
class SessionIDArgumentResolvers: HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(SessionId::class.java) && parameter.parameterType == String::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, message: Message<*>): Any? {
        return SimpMessageHeaderAccessor.getSessionId(message.headers)
    }

}
