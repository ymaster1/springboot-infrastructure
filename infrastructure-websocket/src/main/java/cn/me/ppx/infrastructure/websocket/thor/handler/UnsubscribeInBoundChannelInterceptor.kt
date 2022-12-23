package cn.me.ppx.infrastructure.websocket.thor.handler

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.GenericMessage
import java.util.concurrent.ConcurrentHashMap

/**
 * @author nanfeng
 * @date 2020/10/26 12:13 下午
 */
class UnsubscribeInBoundChannelInterceptor(
    private val messageHandler: MessageHandler
) : ChannelInterceptor {

    private val sessionIdAndSubId2Destination = ConcurrentHashMap<String, MutableMap<String, String>>(128)
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val messageType = SimpMessageHeaderAccessor.getMessageType(message.headers)!!
        val wrap = StompHeaderAccessor.wrap(message)
        when (messageType) {
            SimpMessageType.SUBSCRIBE -> {
                val sessionId = wrap.sessionId ?: return super.preSend(message, channel)
                val subscriptionId = wrap.subscriptionId ?: return super.preSend(message, channel)
                val destination = wrap.destination ?: return super.preSend(message, channel)
                sessionIdAndSubId2Destination.merge(sessionId, hashMapOf(subscriptionId to destination)) { old, new ->
                    old.putAll(new)
                    old
                }
            }
            SimpMessageType.UNSUBSCRIBE -> {
                val sessionId = wrap.sessionId ?: return super.preSend(message, channel)
                val subscriptionId = wrap.subscriptionId ?: return super.preSend(message, channel)
                val path: String = sessionIdAndSubId2Destination[sessionId]?.get(subscriptionId)
                        ?: return super.preSend(message, channel)
                sessionIdAndSubId2Destination[sessionId]?.remove(subscriptionId)
                wrap.destination = path
                return super.preSend(GenericMessage(message.payload, wrap.messageHeaders), channel)
            }

            SimpMessageType.DISCONNECT -> {
                val sessionId = wrap.sessionId ?: return super.preSend(message, channel)
                sessionIdAndSubId2Destination[sessionId]?.mapNotNull { (_, path) ->
                    GenericMessage(Any(), mutableMapOf(
                            StompHeaderAccessor.DESTINATION_HEADER to path,
                            StompHeaderAccessor.MESSAGE_TYPE_HEADER to SimpMessageType.UNSUBSCRIBE,
                            StompHeaderAccessor.SESSION_ID_HEADER to sessionId,
                            StompHeaderAccessor.SESSION_ATTRIBUTES to emptyMap<String, Any>()
                    ) as Map<String, Any>)
                }?.forEach(messageHandler::handleMessage)
                sessionIdAndSubId2Destination.remove(sessionId)
            }
            else -> {
            }
        }
        return super.preSend(message, channel)
    }
}
