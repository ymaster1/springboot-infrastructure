package cn.me.ppx.infrastructure.rbac.annotation;

import java.lang.annotation.*;

/**
 * @author ym
 * @date 2022/11/15 15:38
 * 标记该注解的参数
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}
