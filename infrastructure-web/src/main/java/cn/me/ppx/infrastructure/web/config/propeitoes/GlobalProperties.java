package cn.me.ppx.infrastructure.web.config.propeitoes;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author ym
 * @date 2022/10/29 13:48
 */
@ConfigurationProperties(prefix = "cn.me.ppx")
@ConstructorBinding
public class GlobalProperties {
}
