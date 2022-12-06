package cn.me.ppx.infrastructure.web.ext.listener

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.support.AbstractApplicationContext

/**
 * @author  ym
 * @date  2022/10/29 17:54
 * ContextRefreshedEvent

在调用ConfigurableApplicationContext 接口中的refresh()方法时触发

ContextStartedEvent

在调用ConfigurableApplicationContext的start()方法时触发

ContextStoppedEvent

在调用ConfigurableApplicationContext的stop()方法时触发

ContextClosedEvent

当ApplicationContext被关闭时触发该事件，也就是调用close()方法触发
 *
 */
class WebListener:ApplicationListener<ApplicationStartedEvent> {
    override fun onApplicationEvent(event: ApplicationStartedEvent) {

    }
}