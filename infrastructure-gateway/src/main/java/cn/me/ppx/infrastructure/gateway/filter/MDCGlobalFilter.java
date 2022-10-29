package cn.me.ppx.infrastructure.gateway.filter;

import cn.me.ppx.infrastructure.common.utils.MDCUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ym
 * @date 2022/10/29 11:01
 * 过网关的请求统一生成traceId
 */
@Component
@Order(100)
public class MDCGlobalFilter implements GlobalFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getRequest().mutate().header(MDCUtils.KEY_MSG_ID, MDCUtils.generateId()).build();
        return chain.filter(exchange);
    }

}
