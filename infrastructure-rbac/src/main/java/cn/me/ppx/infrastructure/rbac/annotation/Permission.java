package cn.me.ppx.infrastructure.rbac.annotation;

import java.lang.annotation.*;

/**
 * @author ym
 * @date 2022/11/16 10:12
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
}
