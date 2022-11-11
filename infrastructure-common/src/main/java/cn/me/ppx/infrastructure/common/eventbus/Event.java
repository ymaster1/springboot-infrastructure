package cn.me.ppx.infrastructure.common.eventbus;

import lombok.Data;

import java.util.Properties;

/**
 * @author ym
 * @date 2022/11/10 10:51
 */
@Data
public class Event<T> {
    private Properties properties;

    private T data;
}
