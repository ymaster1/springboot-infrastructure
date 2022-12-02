package cn.me.ppx.infrastructure.websocket.handler.stomp;

import cn.me.ppx.infrastructure.common.dto.BaseResponse;
import cn.me.ppx.infrastructure.websocket.annotation.StompRequestMapping;
import cn.me.ppx.infrastructure.websocket.annotation.StompSubscribeMapping;
import cn.me.ppx.infrastructure.websocket.annotation.StompUnSubscribeMapping;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

/**
 * @author ym
 * @date 2022/12/2 14:52
 */
public class StompMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    private SimpMessageSendingOperations messageSendingOperations;

    public StompMethodReturnValueHandler(SimpMessageSendingOperations messageSendingOperations) {
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(StompRequestMapping.class)
                || returnType.hasMethodAnnotation(StompSubscribeMapping.class)
                || returnType.hasMethodAnnotation(MessageExceptionHandler.class)
                || returnType.hasMethodAnnotation(StompUnSubscribeMapping.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, Message<?> message) throws Exception {
        if (returnValue instanceof BaseResponse) {
        }
    }

}
