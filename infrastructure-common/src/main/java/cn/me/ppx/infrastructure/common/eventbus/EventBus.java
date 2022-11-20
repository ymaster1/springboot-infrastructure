package cn.me.ppx.infrastructure.common.eventbus;

/**
 * @author ym
 * @date 2022/11/10 10:50
 */
public interface EventBus {
    /**
     * 本服务应用解耦
     * 作用参考ApplicationEvent
     *
     * @param topic
     * @param event
     * @param <T>
     */
    public <T> void sendLocal(String topic, Event<T> event);

    /**
     * 每次都会新生成一个queue
     *
     * @param topic
     * @param data
     * @param <T>
     */
    public <T> void sendLocal(String topic, T data);

    /**
     * 用的是一个单例的queue
     *
     * @param topic
     * @param data
     */
    public void sendLocal(String topic, String data);

    /**
     * 广播
     *
     * @param topic
     * @param event
     * @param <T>
     */
    public <T> void sendAll(String topic, Event<T> event);
}
