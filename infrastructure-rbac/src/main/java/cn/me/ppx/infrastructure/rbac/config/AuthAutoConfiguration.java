package cn.me.ppx.infrastructure.rbac.config;

import cn.me.ppx.infrastructure.rbac.handler.AuthHandlerInterceptor;
import cn.me.ppx.infrastructure.rbac.handler.AuthHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author ym
 * @date 2022/11/15 17:41
 */
@Configuration
@EnableConfigurationProperties({AuthProperties.class})
public class AuthAutoConfiguration implements WebMvcConfigurer {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        AuthHandlerMethodArgumentResolver resolver = new AuthHandlerMethodArgumentResolver();
        // 利用工厂给容器外的对象注入所需组件
        applicationContext.getAutowireCapableBeanFactory().autowireBean(resolver);
        resolvers.add(0, resolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthHandlerInterceptor interceptor = new AuthHandlerInterceptor();
        // 利用工厂给容器外的对象注入所需组件
        applicationContext.getAutowireCapableBeanFactory().autowireBean(interceptor);
        registry.addInterceptor(interceptor);
    }
}
