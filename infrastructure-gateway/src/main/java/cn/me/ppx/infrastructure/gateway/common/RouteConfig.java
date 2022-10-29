package cn.me.ppx.infrastructure.gateway.common;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ym
 * @date 2022/10/27 15:02
 * 配置路由策略 RoutePredicateFactory
 */
@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("test", r -> r.path("/web").uri("localhost:8080/web/test")).build();
    }

}
