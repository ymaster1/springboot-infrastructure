package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.Event;
import cn.me.ppx.infrastructure.common.eventbus.EventBus;

/**
 * @author ym
 * @date 2022/11/20 15:15
 */
public class DisruptorEventBus implements EventBus {

    @Override
    public <T> void sendLocal(String topic, Event<T> event) {
        sendLocal(topic, event.getData());
    }

    @Override
    public void sendLocal(String topic, String data) {
        DisruptorQueue<String> queue = DisruptorQueueFactory.getStringHandleEventsQueue(true, new DisruptorHandler<>());
        queue.add(data);
    }

    // todo 单例和泛型已经static不能整合在一起，所以最好的方式是全部以String吧
    @Override
    public <T> void sendLocal(String topic, T data) {
        DisruptorQueue<T> queue = DisruptorQueueFactory.getHandleEventsQueue(true, new DisruptorHandler<>());
        queue.add(data);
    }

    @Override
    public <T> void sendAll(String topic, Event<T> event) {

    }
}
