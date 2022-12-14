package cn.me.ppx.infrastructure.websocket.handler

import cn.me.ppx.infrastructure.websocket.ThorMessage
import cn.me.ppx.infrastructure.websocket.annotation.StompRequestMapping
import cn.me.ppx.infrastructure.websocket.annotation.StompSubscribeMapping
import cn.me.ppx.infrastructure.websocket.annotation.StompUnSubscribeMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageHeaderInitializer
import org.springframework.stereotype.Component

/**
 * 处理 ThorRequestMapping 直接返回消息，并封装结果
 */
@Component
class ThorMethodReturnValueHandler(
    private val messagingTemplate: SimpMessageSendingOperations,
    @Autowired(required = false)
    private val headerInitializer: MessageHeaderInitializer? = null
) : HandlerMethodReturnValueHandler {


    override fun supportsReturnType(returnType: MethodParameter): Boolean {
        return returnType.hasMethodAnnotation(StompRequestMapping::class.java)
                || returnType.hasMethodAnnotation(StompSubscribeMapping::class.java)
                || returnType.hasMethodAnnotation(MessageExceptionHandler::class.java)
                || returnType.hasMethodAnnotation(StompUnSubscribeMapping::class.java)
    }

    override fun handleReturnValue(returnValue: Any?, returnType: MethodParameter, message: Message<*>) {
        // 包装结果
        val payload = if (returnType.parameterType != ThorMessage::class.java) {
            if (returnType.hasMethodAnnotation(MessageExceptionHandler::class.java)) {
                ThorMessage.failed(returnValue)
            } else {
                ThorMessage.success(returnValue)
            }
        } else {
            returnValue
        }

        val headers = message.headers
        val sessionId = SimpMessageHeaderAccessor.getSessionId(headers)!!

        // 处理路径
        val destinations = arrayOf(SimpMessageHeaderAccessor.getDestination(headers))

//        destinations.forEach {
//            this.messagingTemplate.convertAndSendToUser(
//                sessionId, it, payload, createHeaders(sessionId, message)
//            )
//        }
    }

    private fun createHeaders(sessionId: String, message: Message<*>): MessageHeaders {
        val requestMessage = StompHeaderAccessor.wrap(message)
        val stompHeaderAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE)
        headerInitializer?.initHeaders(stompHeaderAccessor)
        stompHeaderAccessor.sessionId = sessionId
        stompHeaderAccessor.receiptId = requestMessage.receipt
        stompHeaderAccessor.setLeaveMutable(true)
        return stompHeaderAccessor.messageHeaders
    }
}
