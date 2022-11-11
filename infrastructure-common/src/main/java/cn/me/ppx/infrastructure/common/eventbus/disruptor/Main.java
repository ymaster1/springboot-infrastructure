package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import java.util.concurrent.TimeUnit;

/**
 * @author ym
 * @date 2022/11/10 10:54
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        DisruptorQueue<String> queue = DisruptorQueueFactory.getHandleEventsQueue(true, new DisruptorHandler<>());
        queue.add("ppx");
        TimeUnit.SECONDS.sleep(3);
        queue.shutdown();
    }
}
