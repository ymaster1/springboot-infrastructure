package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.Event;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author ym
 * @date 2022/11/10 11:00
 * 扩展点
 */
public class DisruptorHandler<T> implements EventHandler<Event<T>>, WorkHandler<Event<T>> {

    @Override
    public void onEvent(Event<T> event, long sequence, boolean endOfBatch) throws Exception {
        String topic = event.getTopic();
        T data = event.getData();
    }

    @Override
    public void onEvent(Event<T> event) throws Exception {
        System.out.println(event.getData().toString());
    }
}
