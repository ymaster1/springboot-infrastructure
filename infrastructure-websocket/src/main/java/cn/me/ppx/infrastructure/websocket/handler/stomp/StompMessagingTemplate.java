package cn.me.ppx.infrastructure.websocket.handler.stomp;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;

/**
 * 取消原有 SimpMessagingTemplate 会强行加上 user_prefix 的功能，并且把 user 设置为 sessionId
 * @author ym
 * @date 2022/12/2 15:42
 */
public class StompMessagingTemplate extends SimpMessagingTemplate {
    public StompMessagingTemplate(MessageChannel messageChannel) {
        super(messageChannel);
    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload, Map<String, Object> headers, MessagePostProcessor postProcessor) throws MessagingException {
        String destinationStrict = destination.startsWith("/") ? destination : "/" + destination;
        if (headers != null) {
            headers.put(SimpMessageHeaderAccessor.SESSION_ID_HEADER, user);
        }
        Message<?> message = doConvert(payload, headers, postProcessor);
        send(destinationStrict, message);
    }
}
