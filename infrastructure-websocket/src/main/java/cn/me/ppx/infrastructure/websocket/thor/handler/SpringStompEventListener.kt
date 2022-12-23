package cn.me.ppx.infrastructure.websocket.thor.handler

import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import org.springframework.web.socket.messaging.SessionSubscribeEvent
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent

/**
 * @author nanfeng
 * @date 2020/6/1 2:30 下午
 */
class SpringStompEventListener(
        private val delegate: ThorEventListener?
) {

    val subScribeId2Path = HashMap<String, String>(128)

    @EventListener
    fun onBrokerAvailable(event: BrokerAvailabilityEvent) {
        delegate?.onBrokerAvailable(event)
    }


    @EventListener
    fun onSessionConnected(event: SessionConnectedEvent) {
        delegate?.onSessionConnected(event)
    }

    @EventListener
    fun onSessionSubscribe(event: SessionSubscribeEvent) {
        delegate?.onSessionSubscribe(event)
    }

    @EventListener
    fun onSessionUnsubscribe(event: SessionUnsubscribeEvent) {
        delegate?.onSessionUnsubscribe(event)
    }

    @EventListener
    fun onSessionDisconnect(event: SessionDisconnectEvent) {
        delegate?.onSessionDisconnect(event)
    }

}
