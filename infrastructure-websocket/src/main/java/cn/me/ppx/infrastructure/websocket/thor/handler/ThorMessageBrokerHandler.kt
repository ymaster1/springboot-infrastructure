package cn.me.ppx.infrastructure.websocket.thor.handler

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler
import org.springframework.messaging.simp.broker.SubscriptionRegistry
import org.springframework.messaging.simp.stomp.StompCommand

/**
 * 处理 Subscribe, Connect 等等基础命令的 Handler
 */
class ThorMessageBrokerHandler(
    clientInboundChannel: SubscribableChannel,
    clientOutboundChannel: MessageChannel,
    brokerChannel: SubscribableChannel,
    destinationPrefixes: MutableCollection<String>
) : SimpleBrokerMessageHandler(clientInboundChannel, clientOutboundChannel, brokerChannel, destinationPrefixes) {


    private val COMMAND_HEADER = "stompCommand"

    override fun checkDestinationPrefix(destination: String?): Boolean {
        return true
    }





    override fun handleMessageInternal(message: Message<*>) {
        val messageType = SimpMessageHeaderAccessor.getMessageType(message.headers)!!
        val command = message.headers[COMMAND_HEADER] as? StompCommand?

        // Client 发送的消息就不再次发布给用户
        if (messageType == SimpMessageType.MESSAGE && StompCommand.SEND == command) {
            return
        }

        super.handleMessageInternal(message)
    }


}
