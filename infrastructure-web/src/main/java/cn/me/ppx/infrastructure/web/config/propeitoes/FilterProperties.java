package cn.me.ppx.infrastructure.web.config.propeitoes;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author ym
 * @date 2022/10/29 13:46
 */
@ConfigurationProperties(prefix = "cn.me.ppx.filter", ignoreInvalidFields = true)
@Data
@Validated
public class FilterProperties {
    @NotNull
    private Boolean auth = true;
    @NotNull
    private Boolean logParam = false;
}
