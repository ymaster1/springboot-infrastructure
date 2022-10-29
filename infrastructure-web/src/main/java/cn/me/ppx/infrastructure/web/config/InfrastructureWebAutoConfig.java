package cn.me.ppx.infrastructure.web.config;

import cn.me.ppx.infrastructure.web.config.propeitoes.FilterProperties;
import cn.me.ppx.infrastructure.web.framework.servlet.RequestContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ym
 * @date 2022/9/30 12:50
 */
@Configuration
@EnableConfigurationProperties({FilterProperties.class})
public class InfrastructureWebAutoConfig {
    @Autowired
    private FilterProperties filterProperties;

    @Bean
    public FilterRegistrationBean<RequestContextFilter> requestFilter() {
        RequestContextFilter filter = new RequestContextFilter();
        Set<String> set = new HashSet<>();
        set.add("*.icon");
        set.add("*.ico");
        set.add("*.html");
        filter.setExcludeUris(set);
        filter.setAuth(filterProperties.getAuth());
        filter.setLogParams(filterProperties.getLogParam());
        FilterRegistrationBean<RequestContextFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("requestFilter");
        return registrationBean;
    }
}
