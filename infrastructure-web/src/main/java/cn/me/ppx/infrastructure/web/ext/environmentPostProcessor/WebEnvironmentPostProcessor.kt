package cn.me.ppx.infrastructure.web.ext.environmentPostProcessor

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertySource
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.util.Assert
import java.io.IOException

/**
 * @author  ym
 * @date  2022/10/29 17:41
 * 有些环境变量可能需要在refresh之前被读取到便于使用,
 */
class WebEnvironmentPostProcessor : EnvironmentPostProcessor {

    private val loader = YamlPropertySourceLoader()

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
//        val path: Resource = ClassPathResource("com/example/myapp/config.yml")
//        val propertySource = loadYaml(path)
//        environment.propertySources.addLast(propertySource)
        println("env process")
//        environment.setRequiredProperties("ppx")
    }

    private fun loadYaml(path: Resource): PropertySource<*> {
        Assert.isTrue(path.exists()) { "Resource $path does not exist" }
        return try {
            loader.load("custom-resource", path)[0]
        } catch (ex: IOException) {
            throw IllegalStateException("Failed to load yaml configuration from $path", ex)
        }
    }

}