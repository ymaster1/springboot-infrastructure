package cn.me.ppx.infrastructure.rbac.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ym
 * @date 2022/11/15 17:41
 */
@Configuration
@EnableConfigurationProperties({AuthProperties.class})
public class AuthAutoConfiguration {
}
