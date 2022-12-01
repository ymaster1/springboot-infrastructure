package cn.me.ppx.infrastructure.websocket.handler

import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import org.springframework.web.socket.messaging.SessionSubscribeEvent
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent


interface ThorEventListener {
    fun onBrokerAvailable(event: BrokerAvailabilityEvent) {

    }


    fun onSessionConnected(event: SessionConnectedEvent) {

    }

    fun onSessionSubscribe(event: SessionSubscribeEvent) {

    }

    fun onSessionUnsubscribe(event: SessionUnsubscribeEvent) {

    }

    fun onSessionDisconnect(event: SessionDisconnectEvent) {

    }

}