package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.Event;
import cn.me.ppx.infrastructure.common.eventbus.EventBus;
import cn.me.ppx.infrastructure.common.exception.SysException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ym
 * @date 2022/11/20 15:15
 */
public class DisruptorEventBus implements EventBus {
    @Override
    public <T> void sendLocal(Event<T> event) throws SysException {
        sendLocal(event.getTopic(), JSONObject.toJSON(event.getData()));
    }

    @Override
    public void sendLocal(String topic, String data) throws SysException {
        DisruptorQueue<String> queue = DisruptorQueueFactory.getStringHandleEventsQueue(true, new DisruptorHandler<>());
        queue.add(topic, data);
    }

    // todo 单例和泛型已经static不能整合在一起，所以最好的方式是全部以String吧
    @Override
    public <T> void sendLocal(String topic, T data) throws SysException {
        if (data instanceof String) {
            sendLocal(topic, (String) data);
        } else {
            sendLocal(topic, (String) JSONObject.toJSON(data));
        }
    }

    @Override
    public <T> void sendAll(String topic, Event<T> event) {

    }
}
