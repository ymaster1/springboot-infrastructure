package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.Event;
import com.lmax.disruptor.EventFactory;

import java.util.Properties;

/**
 * @author ym
 * @date 2022/11/10 10:57
 */
public class DisruptorEventFactory<T> implements EventFactory<Event<T>> {
    @Override
    public Event<T> newInstance() {
        Event<T> event = new Event<>();
        Properties properties = new Properties();
        event.setProperties(properties);
        return event;
    }
}
