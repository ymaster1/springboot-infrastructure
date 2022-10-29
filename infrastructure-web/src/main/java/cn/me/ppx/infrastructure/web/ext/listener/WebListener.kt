package cn.me.ppx.infrastructure.web.ext.listener

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.support.AbstractApplicationContext

/**
 * @author  ym
 * @date  2022/10/29 17:54
 *
 */
class WebListener:ApplicationListener<ApplicationStartedEvent> {
    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        TODO("Not yet implemented")
    }
}