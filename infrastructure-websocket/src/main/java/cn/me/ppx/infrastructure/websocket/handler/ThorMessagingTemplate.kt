package cn.me.ppx.infrastructure.websocket.handler

import org.springframework.messaging.MessageChannel
import org.springframework.messaging.core.MessagePostProcessor
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate

/**
 * 取消原有 SimpMessagingTemplate 会强行加上 user_prefix 的功能，并且把 user 设置为 sessionId
 */
class ThorMessagingTemplate(messageChannel: MessageChannel) : SimpMessagingTemplate(messageChannel) {

    override fun convertAndSendToUser(user: String, destination: String, payload: Any, headers: MutableMap<String, Any>?, postProcessor: MessagePostProcessor?) {
        val destinationStrict = if (destination.startsWith("/")) destination else "/$destination"
        val finalHeader = headers?.toMutableMap() ?: mutableMapOf()
        finalHeader[SimpMessageHeaderAccessor.SESSION_ID_HEADER] = user
        val message = doConvert(payload, finalHeader, postProcessor)
        send(destinationStrict, message)
    }

}