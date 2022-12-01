package cn.me.ppx.infrastructure.websocket.handler

import cn.me.ppx.infrastructure.websocket.ThorMessage
import cn.me.ppx.infrastructure.websocket.annotation.ThorRequestMapping
import cn.me.ppx.infrastructure.websocket.annotation.ThorStompController
import cn.me.ppx.infrastructure.websocket.annotation.ThorSubscribeMapping
import cn.me.ppx.infrastructure.websocket.annotation.ThorUnSubscribeMapping
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.handler.DestinationPatternsMessageCondition
import org.springframework.messaging.simp.*
import org.springframework.web.socket.messaging.WebSocketAnnotationMethodMessageHandler
import java.lang.reflect.Method

/**
 * 主要是为了注册 ThorStompController 与 ThorRequestMapping，使其能接受消息
 * 还有处理 404
 */
class ThorAnnotationMethodMessageHandler(
        clientInChannel: SubscribableChannel,
        clientOutChannel: MessageChannel,
        private val brokerTemplate: SimpMessageSendingOperations
) : WebSocketAnnotationMethodMessageHandler(clientInChannel, clientOutChannel, brokerTemplate) {

    override fun handleNoMatch(ts: MutableSet<SimpMessageMappingInfo>, lookupDestination: String, message: Message<*>) {
        super.handleNoMatch(ts, lookupDestination, message)
        if (!ts.any { it.destinationConditions.getMatchingCondition(message) != null }) {
            this.brokerTemplate.convertAndSendToUser(
                    SimpMessageHeaderAccessor.getSessionId(message.headers)!!, lookupDestination, ThorMessage.notFound())
        }
    }

    override fun isHandler(beanType: Class<*>): Boolean {
        return AnnotatedElementUtils.hasAnnotation(beanType, ThorStompController::class.java)
    }

    override fun getMappingForMethod(method: Method, handlerType: Class<*>): SimpMessageMappingInfo? {
        val messageAnn = AnnotatedElementUtils.findMergedAnnotation(method, ThorRequestMapping::class.java)
        if (messageAnn != null) {
            val typeAnn = AnnotatedElementUtils.findMergedAnnotation(handlerType, ThorRequestMapping::class.java)
            // Only actually register it if there are destinations specified;
            // otherwise @MessageMapping is just being used as a (meta-annotation) marker.
            var result = createMessageMappingCondition(messageAnn.value as Array<String>)
            if (typeAnn != null) {
                result = createMessageMappingCondition(typeAnn.value as Array<String>).combine(result)
            }
            return result
        }

        val subscribeAnn = AnnotatedElementUtils.findMergedAnnotation(method, ThorSubscribeMapping::class.java)
        if (subscribeAnn != null) {
            val typeAnn = AnnotatedElementUtils.findMergedAnnotation(handlerType, ThorSubscribeMapping::class.java)
            // Only actually register it if there are destinations specified;
            // otherwise @MessageMapping is just being used as a (meta-annotation) marker.
            var result = createSubscribeMappingCondition(subscribeAnn.value as Array<String>)
            if (typeAnn != null) {
                result = createSubscribeMappingCondition(typeAnn.value as Array<String>).combine(result)
            }
            return result
        }

        val unSubscribeMapping = AnnotatedElementUtils.findMergedAnnotation(method, ThorUnSubscribeMapping::class.java)
        if (unSubscribeMapping != null) {
            val typeAnn = AnnotatedElementUtils.findMergedAnnotation(handlerType, ThorUnSubscribeMapping::class.java)
            // Only actually register it if there are destinations specified;
            // otherwise @MessageMapping is just being used as a (meta-annotation) marker.
            var result = createUnSubscribeMappingCondition(unSubscribeMapping.value as Array<String>)
            if (typeAnn != null) {
                result = createUnSubscribeMappingCondition(typeAnn.value as Array<String>).combine(result)
            }
            return result
        }
        return null
    }

    private fun createMessageMappingCondition(destinations: Array<String>): SimpMessageMappingInfo {
        val resolvedDestinations = resolveEmbeddedValuesInDestinations(destinations)
        return SimpMessageMappingInfo(SimpMessageTypeMessageCondition.MESSAGE,
                DestinationPatternsMessageCondition(resolvedDestinations, pathMatcher))
    }
    private fun createUnSubscribeMappingCondition(destinations: Array<String>): SimpMessageMappingInfo {
        val resolvedDestinations = resolveEmbeddedValuesInDestinations(destinations)
        return SimpMessageMappingInfo(SimpMessageTypeMessageCondition(SimpMessageType.UNSUBSCRIBE),
                DestinationPatternsMessageCondition(resolvedDestinations, pathMatcher))
    }

    private fun createSubscribeMappingCondition(destinations: Array<String>): SimpMessageMappingInfo {
        val resolvedDestinations = resolveEmbeddedValuesInDestinations(destinations)
        return SimpMessageMappingInfo(SimpMessageTypeMessageCondition.SUBSCRIBE,
                DestinationPatternsMessageCondition(resolvedDestinations, pathMatcher))
    }

}
