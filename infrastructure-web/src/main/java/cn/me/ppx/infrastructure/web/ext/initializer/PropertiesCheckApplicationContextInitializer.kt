package cn.me.ppx.infrastructure.web.ext.initializer

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

/**
 * @author  ym
 * @date  2022/12/5 15:44
 * 可以在刷新之前制定一些必填的环境变量，refresh的时候会自动检查
 */
class PropertiesCheckApplicationContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val environment = applicationContext.environment
        // 在refresh之前设置的必要环境变量会被检查
//        environment.setRequiredProperties("ppx")
    }
}