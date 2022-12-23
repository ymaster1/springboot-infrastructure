package me.jinuo.imf.thor

import me.jinuo.imf.thor.handler.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.core.task.TaskExecutor
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.converter.CompositeMessageConverter
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler
import org.springframework.messaging.simp.user.UserDestinationResolver
import org.springframework.messaging.support.AbstractSubscribableChannel
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import java.util.concurrent.ThreadPoolExecutor

/**
 * handler 里面一堆组件的大注册器
 */
@Configuration
@ComponentScan
class ThorMessageBrokerConfiguration(
    @Autowired(required = false)
    private val customPathInterceptors: List<CustomPathHandshakeInterceptor>?
) : DelegatingWebSocketMessageBrokerConfiguration(), ApplicationContextAware, CommandLineRunner {
    @Autowired
    private lateinit var mappingGson2MessageConverter: MappingGson2MessageConverter


    /**
     * 由调用方实现
     */
    @Bean
    fun springStompEventListener(@Autowired(required = false) thorEventListener: ThorEventListener?): SpringStompEventListener {
        return SpringStompEventListener(thorEventListener)
    }

    @Bean
    fun gson2MessageConverter(): MappingGson2MessageConverter {
        return MappingGson2MessageConverter()
    }

    override fun configureMessageConverters(messageConverters: MutableList<MessageConverter>): Boolean {
        messageConverters.add(mappingGson2MessageConverter)
        return true
    }

    override fun simpleBrokerMessageHandler(
        clientInboundChannel: AbstractSubscribableChannel,
        clientOutboundChannel: AbstractSubscribableChannel,
        brokerChannel: AbstractSubscribableChannel,
        userDestinationResolver: UserDestinationResolver
    ): AbstractBrokerMessageHandler? {
        val handler =
            ThorMessageBrokerHandler(clientInboundChannel, clientOutboundChannel, brokerChannel, mutableListOf())
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.threadNamePrefix = "MessageBroker-"
        scheduler.poolSize = Runtime.getRuntime().availableProcessors()
        scheduler.isRemoveOnCancelPolicy = true
        scheduler.initialize()
        handler.taskScheduler = scheduler
        handler.heartbeatValue = longArrayOf(10000L, 10000L)
        handler.subscriptionRegistry = ThorSubscriptionRegistry()
        return handler
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        if (applicationContext != null) {
            ThorControllerResolvers(applicationContext!!, customPathInterceptors).resolveThorController(registry)
        }
    }


    override fun createAnnotationMethodMessageHandler(
        clientInboundChannel: AbstractSubscribableChannel,
        clientOutboundChannel: AbstractSubscribableChannel,
        brokerMessagingTemplate: SimpMessagingTemplate
    ): SimpAnnotationMethodMessageHandler {
        return ThorAnnotationMethodMessageHandler(
            clientInboundChannel,
            clientOutboundChannel,
            brokerMessagingTemplate
        )
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(SessionIDArgumentResolvers())
        argumentResolvers.add(PathVariableArgumentResolvers(DefaultConversionService.getSharedInstance()))
    }

    override fun addReturnValueHandlers(returnValueHandlers: MutableList<HandlerMethodReturnValueHandler>) {
        val bean = applicationContext!!.getBean("brokerMessagingTemplate")
        returnValueHandlers.add(ThorMethodReturnValueHandler(bean as SimpMessagingTemplate))
    }


    override fun brokerMessagingTemplate(
        brokerChannel: AbstractSubscribableChannel,
        clientInboundChannel: AbstractSubscribableChannel,
        clientOutboundChannel: AbstractSubscribableChannel,
        brokerMessageConverter: CompositeMessageConverter
    ): SimpMessagingTemplate {
        val template = ThorMessagingTemplate(brokerChannel)
        template.messageConverter = brokerMessageConverter()
        return template
    }

    override fun clientOutboundChannelExecutor(): TaskExecutor? {
        val executor = super.clientInboundChannelExecutor() as ThreadPoolTaskExecutor
        executor.setQueueCapacity(0)
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.DiscardPolicy())
        return executor
    }

    override fun clientInboundChannelExecutor(): TaskExecutor {
        val executor = super.clientInboundChannelExecutor() as ThreadPoolTaskExecutor
        executor.setQueueCapacity(0)
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.DiscardPolicy())
        return executor
    }

    override fun clientInboundChannel(clientInboundChannelExecutor: TaskExecutor): AbstractSubscribableChannel {
        return super.clientInboundChannel(clientInboundChannelExecutor)
    }

    override fun run(vararg args: String?) {
        val messageHandler = applicationContext.getBean("simpAnnotationMethodMessageHandler") as MessageHandler

        (applicationContext.getBean("clientInboundChannel") as AbstractSubscribableChannel).addInterceptor(
            0,
            UnsubscribeInBoundChannelInterceptor(messageHandler)
        )
    }
}
