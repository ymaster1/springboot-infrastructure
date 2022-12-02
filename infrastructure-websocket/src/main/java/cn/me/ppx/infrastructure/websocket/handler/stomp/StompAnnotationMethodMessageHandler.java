package cn.me.ppx.infrastructure.websocket.handler.stomp;

import cn.me.ppx.infrastructure.websocket.ThorMessage;
import cn.me.ppx.infrastructure.websocket.annotation.StompController;
import cn.me.ppx.infrastructure.websocket.annotation.StompRequestMapping;
import cn.me.ppx.infrastructure.websocket.annotation.StompSubscribeMapping;
import cn.me.ppx.infrastructure.websocket.annotation.StompUnSubscribeMapping;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.DestinationPatternsMessageCondition;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.*;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.socket.messaging.WebSocketAnnotationMethodMessageHandler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author ym
 * @date 2022/12/2 10:25
 */
public class StompAnnotationMethodMessageHandler extends WebSocketAnnotationMethodMessageHandler {
    private SimpMessageSendingOperations brokerTemplate;

    public StompAnnotationMethodMessageHandler(SubscribableChannel clientInChannel, MessageChannel clientOutChannel, SimpMessageSendingOperations brokerTemplate) {
        super(clientInChannel, clientOutChannel, brokerTemplate);
        this.brokerTemplate = brokerTemplate;
    }

    /**
     * 自定义参数解析
     * @param customArgumentResolvers
     */
    @Override
    public void setCustomArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> customArgumentResolvers) {
        customArgumentResolvers.add(new SessionIdArgumentResolver());
    }

    /**
     * 统一返回值处理
     * @param customReturnValueHandlers
     */
    @Override
    public void setCustomReturnValueHandlers(@NotNull List<HandlerMethodReturnValueHandler> customReturnValueHandlers) {
        customReturnValueHandlers.add(0, new StompMethodReturnValueHandler(brokerTemplate));
    }

    /**
     * 没有匹配上路径的时候，默认不处理
     */
    @Override
    protected void handleNoMatch(@NotNull Set<SimpMessageMappingInfo> simpMessageMappingInfos, @NotNull String lookupDestination, @NotNull Message<?> message) {
//        if (!ts.any { it.destinationConditions.getMatchingCondition(message) != null }) {
//            this.brokerTemplate.convertAndSendToUser(
//                    SimpMessageHeaderAccessor.getSessionId(message.headers)!!, lookupDestination, ThorMessage.notFound())
//        }
        super.handleNoMatch(simpMessageMappingInfos, lookupDestination, message);
    }

    /**
     * 自定义两种处理消息的注解，默认是：
     *
     * @MessageMapping
     * @SubscribeMapping
     */

    @Override
    protected SimpMessageMappingInfo getMappingForMethod(@NotNull Method method, @NotNull Class<?> handlerType) {
        StompRequestMapping messageAnn = AnnotatedElementUtils.findMergedAnnotation(method, StompRequestMapping.class);
        if (messageAnn != null) {
            StompRequestMapping typeAnn = AnnotatedElementUtils.findMergedAnnotation(handlerType, StompRequestMapping.class);
            if (messageAnn.value().length > 0 || (typeAnn != null && typeAnn.value().length > 0)) {
                SimpMessageMappingInfo result = createMessageMappingCondition(messageAnn.value());
                if (typeAnn != null) {
                    result = createMessageMappingCondition(typeAnn.value()).combine(result);
                }
                return result;
            }
        }

        StompSubscribeMapping subscribeAnn = AnnotatedElementUtils.findMergedAnnotation(method, StompSubscribeMapping.class);
        if (subscribeAnn != null) {
            StompSubscribeMapping typeAnn = AnnotatedElementUtils.findMergedAnnotation(handlerType, StompSubscribeMapping.class);
            if (subscribeAnn.value().length > 0 || (typeAnn != null && typeAnn.value().length > 0)) {
                SimpMessageMappingInfo result = createSubscribeMappingCondition(subscribeAnn.value());
                if (typeAnn != null) {
                    result = createMessageMappingCondition(typeAnn.value()).combine(result);
                }
                return result;
            }
        }

        StompUnSubscribeMapping unSubscribeAnn = AnnotatedElementUtils.findMergedAnnotation(method, StompUnSubscribeMapping.class);
        if (unSubscribeAnn != null) {
            StompUnSubscribeMapping typeAnn = AnnotatedElementUtils.findMergedAnnotation(handlerType, StompUnSubscribeMapping.class);

            if (unSubscribeAnn.value().length > 0 || (typeAnn != null && typeAnn.value().length > 0)) {
                SimpMessageMappingInfo result = createUnSubscribeMappingCondition(unSubscribeAnn.value());
                if (typeAnn != null) {
                    result = createMessageMappingCondition(typeAnn.value()).combine(result);
                }
                return result;
            }
        }

        return null;
    }

    /**
     * 处理stomp消息的接收器需要标记该注解，默认是@controller,可自定义，为了不跟webmvc混淆，建议加个前缀
     */
    @Override
    protected boolean isHandler(@NotNull Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, StompController.class);
    }


    private SimpMessageMappingInfo createMessageMappingCondition(String[] destinations) {
        String[] resolvedDestinations = resolveEmbeddedValuesInDestinations(destinations);
        return new SimpMessageMappingInfo(SimpMessageTypeMessageCondition.MESSAGE,
                new DestinationPatternsMessageCondition(resolvedDestinations, getPathMatcher()));
    }

    private SimpMessageMappingInfo createSubscribeMappingCondition(String[] destinations) {
        String[] resolvedDestinations = resolveEmbeddedValuesInDestinations(destinations);
        return new SimpMessageMappingInfo(SimpMessageTypeMessageCondition.SUBSCRIBE,
                new DestinationPatternsMessageCondition(resolvedDestinations, getPathMatcher()));
    }

    private SimpMessageMappingInfo createUnSubscribeMappingCondition(String[] destinations) {
        String[] resolvedDestinations = resolveEmbeddedValuesInDestinations(destinations);
        return new SimpMessageMappingInfo(new SimpMessageTypeMessageCondition(SimpMessageType.UNSUBSCRIBE),
                new DestinationPatternsMessageCondition(resolvedDestinations, getPathMatcher()));
    }
}
