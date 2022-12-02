package cn.me.ppx.infrastructure.websocket.handler.stomp;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;

import java.util.Collection;

/**
 * @author ym
 * @date 2022/12/2 11:13
 * broker 保存了每个链接的session信息 sessionId -> SessionInfo
 */
public class StompBrokerMessageHandler extends SimpleBrokerMessageHandler {
    private static final String STOMP_COMMAND = "stompCommand";

    /**
     * 代理服务器的消息处理逻辑,根据消息类型，默认处理了MESSAGE,CONNECT,DISCONNECT,SUBSCRIBE,UNSUBSCRIBE这五种类型的信息
     *
     * @param message
     */
    @Override
    protected void handleMessageInternal(Message<?> message) {
        SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(message.getHeaders());
        Object stompCommand = message.getHeaders().get(STOMP_COMMAND);
        // client发送的消息就不再发回去了，没意思
        if (stompCommand == StompCommand.SEND.name() && messageType == SimpMessageType.MESSAGE) {
            return;
        }
        super.handleMessageInternal(message);
    }

    /**
     * 返回true的时候才执行上面的消息处理逻辑
     *
     * @param destination 目的地
     * @return 是否继续处理消息
     */
    @Override
    protected boolean checkDestinationPrefix(String destination) {
        return super.checkDestinationPrefix(destination);
    }

    /**
     * Create a SimpleBrokerMessageHandler instance with the given message channels
     * and destination prefixes.
     *
     * @param clientInboundChannel  the channel for receiving messages from clients (e.g. WebSocket clients)
     * @param clientOutboundChannel the channel for sending messages to clients (e.g. WebSocket clients)
     * @param brokerChannel         the channel for the application to send messages to the broker
     * @param destinationPrefixes   prefixes to use to filter out messages
     */
    public StompBrokerMessageHandler(SubscribableChannel clientInboundChannel, MessageChannel clientOutboundChannel, SubscribableChannel brokerChannel, Collection<String> destinationPrefixes) {
        super(clientInboundChannel, clientOutboundChannel, brokerChannel, destinationPrefixes);
    }
}
