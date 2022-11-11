package cn.me.ppx.infrastructure.common.eventbus;

/**
 * @author ym
 * @date 2022/11/10 10:50
 */
public interface EventBus {
    /**
     * 向指定topic发送消息
     *
     * @param topic
     * @param event
     * @param <T>
     */
    public <T> void send(String topic, Event<T> event);

    /**
     * 广播
     * @param topic
     * @param event
     * @param <T>
     */
    public <T> void  sendAll(String topic,Event<T> event);
}
