package cn.me.ppx.infrastructure.websocket.handler.stomp;

import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.broker.DefaultSubscriptionRegistry;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author ym
 * @date 2022/12/2 11:36
 */
public class StompSubscriptionRegistry extends DefaultSubscriptionRegistry {
    /**
     * 广播消息的时候，看看消息里有没有指定 sessionId，如果有，就过滤一遍，只给这个人发
     *
     * @param destination
     * @param message
     * @return
     */
    @NotNull
    @Override
    protected MultiValueMap<String, String> findSubscriptionsInternal(@NotNull String destination, @NotNull Message<?> message) {
        MultiValueMap<String, String> map = super.findSubscriptionsInternal(destination, message);
        String sessionId = SimpMessageHeaderAccessor.getSessionId(message.getHeaders()) == null ? SimpMessageHeaderAccessor.getFirstNativeHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER, message.getHeaders()) : SimpMessageHeaderAccessor.getSessionId(message.getHeaders());
        if (sessionId != null && map.get(sessionId) != null) {
            LinkedMultiValueMap<String, String> newMap = new LinkedMultiValueMap<>();
            newMap.put(sessionId, map.get(sessionId));
            return newMap;
        }
        return map;
    }
}
