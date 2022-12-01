package cn.me.ppx.infrastructure.websocket.handler

import org.springframework.messaging.Message
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.broker.DefaultSubscriptionRegistry
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver
import org.springframework.messaging.simp.user.SimpUserRegistry
import org.springframework.messaging.simp.user.UserDestinationResult
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * 广播消息的时候，看看消息里有没有指定 sessionId，如果有，就过滤一遍，只给这个人发
 */
class ThorSubscriptionRegistry : DefaultSubscriptionRegistry() {

    override fun findSubscriptionsInternal(destination: String, message: Message<*>): MultiValueMap<String, String> {
        val map = super.findSubscriptionsInternal(destination, message)
        val sessionId = SimpMessageHeaderAccessor.getSessionId(message.headers)
                ?: SimpMessageHeaderAccessor.getFirstNativeHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER, message.headers)
        if (sessionId != null && map[sessionId] != null) {
            val newMap = LinkedMultiValueMap<String, String>()
            newMap[sessionId] = map[sessionId]
            return newMap
        }
        return map
    }


}
