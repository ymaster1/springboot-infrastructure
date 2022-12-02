package cn.me.ppx.infrastructure.websocket.handler.stomp;

import cn.me.ppx.infrastructure.websocket.annotation.SessionId;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

/**
 * @author ym
 * @date 2022/12/2 10:45
 * 获取sessionID
 */
public class SessionIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionId.class) && parameter.getParameterType() == String.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        return SimpMessageHeaderAccessor.getSessionId(message.getHeaders());
    }
}
