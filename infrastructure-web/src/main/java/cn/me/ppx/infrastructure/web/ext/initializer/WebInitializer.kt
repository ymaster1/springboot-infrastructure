package cn.me.ppx.infrastructure.web.ext.initializer

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.AbstractApplicationContext

/**
 * @author  ym
 * @date  2022/10/29 17:51
 *  此时传入的ConfigurableApplicationContext并没有调用过refresh方法，
 * 也就是里面是没有Bean对象的，但是可以手动注册一些单例bean,一般这个接口是用来配置ConfigurableApplicationContext，而不是用来获取Bean的。
 */
class WebInitializer : ApplicationContextInitializer<AbstractApplicationContext> {
    override fun initialize(applicationContext: AbstractApplicationContext) {
       applicationContext.beanFactory.registerSingleton("ppx_singleton", Any())
    }
}