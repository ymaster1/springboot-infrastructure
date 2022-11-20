package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.EventBus;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ym
 * @date 2022/11/10 10:54
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<EventBus> eventBuses = SpringFactoriesLoader.loadFactories(EventBus.class, null);
        EventBus eventBus = eventBuses.get(0);
        eventBus.sendLocal("topic", "sad");
//        disruptor();
    }

    public static void disruptor() throws InterruptedException {
        DisruptorQueue<String> queue = DisruptorQueueFactory.getHandleEventsQueue(true, new DisruptorHandler<>());
        queue.add("ppx");
        TimeUnit.SECONDS.sleep(3);
        queue.shutdown();
    }
}
