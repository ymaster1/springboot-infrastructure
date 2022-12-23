package cn.me.ppx.infrastructure.websocket.thor.handler

import org.springframework.web.socket.server.HandshakeInterceptor

/**
 * @author nanfeng
 * @date 2020/6/15 3:17 下午
 */
interface CustomPathHandshakeInterceptor : HandshakeInterceptor {
    fun supportPath(): List<String>
}
