package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.EventBus;
import cn.me.ppx.infrastructure.common.exception.SysException;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * @author ym
 * @date 2022/11/10 10:54
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, SysException {
        List<EventBus> eventBuses = SpringFactoriesLoader.loadFactories(EventBus.class, null);
        EventBus eventBus = eventBuses.get(0);
        eventBus.sendLocal("topic", "sad");
    }
}
