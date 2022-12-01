package cn.me.ppx.infrastructure.websocket.handler

import org.springframework.web.socket.server.HandshakeInterceptor


interface CustomPathHandshakeInterceptor : HandshakeInterceptor {
    fun supportPath(): List<String>
}
