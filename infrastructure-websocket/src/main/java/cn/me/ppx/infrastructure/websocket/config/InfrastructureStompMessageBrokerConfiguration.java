package cn.me.ppx.infrastructure.websocket.config;

import cn.me.ppx.infrastructure.websocket.handler.ThorControllerResolvers;
import cn.me.ppx.infrastructure.websocket.handler.stomp.StompAnnotationMethodMessageHandler;
import cn.me.ppx.infrastructure.websocket.handler.stomp.StompBrokerMessageHandler;
import cn.me.ppx.infrastructure.websocket.handler.stomp.StompMessagingTemplate;
import cn.me.ppx.infrastructure.websocket.handler.stomp.StompSubscriptionRegistry;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ym
 * @date 2022/12/2 13:46
 */
@Configuration
public class InfrastructureStompMessageBrokerConfiguration extends DelegatingWebSocketMessageBrokerConfiguration {
    /**
     * 消息发送工具
     *
     * @param brokerChannel
     * @param clientInboundChannel
     * @param clientOutboundChannel
     * @param brokerMessageConverter
     * @return
     */
    @NotNull
    @Override
    public SimpMessagingTemplate brokerMessagingTemplate(@NotNull AbstractSubscribableChannel brokerChannel, @NotNull AbstractSubscribableChannel clientInboundChannel, @NotNull AbstractSubscribableChannel clientOutboundChannel, @NotNull CompositeMessageConverter brokerMessageConverter) {
        StompMessagingTemplate template = new StompMessagingTemplate(brokerChannel);
        template.setMessageConverter(brokerMessageConverter());
        return template;
    }


    /**
     * 代理服务器的消息处理逻辑
     *
     * @param clientInboundChannel
     * @param clientOutboundChannel
     * @param brokerChannel
     * @param userDestinationResolver
     * @return
     */
    @Override
    public AbstractBrokerMessageHandler simpleBrokerMessageHandler(@NotNull AbstractSubscribableChannel clientInboundChannel, @NotNull AbstractSubscribableChannel clientOutboundChannel, @NotNull AbstractSubscribableChannel brokerChannel, @NotNull UserDestinationResolver userDestinationResolver) {
        StompBrokerMessageHandler handler = new StompBrokerMessageHandler(clientInboundChannel, clientOutboundChannel, brokerChannel, new ArrayList<>());
        handler.setSubscriptionRegistry(new StompSubscriptionRegistry());
        return handler;
    }

    /**
     * 方法的消息处理器
     *
     * @param clientInboundChannel
     * @param clientOutboundChannel
     * @param brokerMessagingTemplate
     * @return
     */
    @NotNull
    @Override
    protected SimpAnnotationMethodMessageHandler createAnnotationMethodMessageHandler(@NotNull AbstractSubscribableChannel clientInboundChannel, @NotNull AbstractSubscribableChannel clientOutboundChannel, @NotNull SimpMessagingTemplate brokerMessagingTemplate) {
        return new StompAnnotationMethodMessageHandler(clientInboundChannel, clientOutboundChannel, brokerMessagingTemplate);
    }

    /**
     * 消息转换
     *
     * @param messageConverters the list to add converters to, initially empty
     * @return
     */
    @Override
    protected boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(new MappingJackson2MessageConverter());
        return true;
    }

    /**
     * 注册链接端点
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").setAllowedOrigins("*").withSockJS();
        new ThorControllerResolvers(Objects.requireNonNull(getApplicationContext()), new ArrayList<>());
    }

}
