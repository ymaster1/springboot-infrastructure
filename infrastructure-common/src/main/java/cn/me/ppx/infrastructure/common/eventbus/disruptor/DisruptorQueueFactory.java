package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.Event;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/**
 * @author ym
 * @date 2022/11/10 14:22
 */
public class DisruptorQueueFactory {
    private DisruptorQueueFactory() {
    }

    private static volatile DisruptorQueue<String> stringQueue;

    // 创建"点对电模式"的操作队列，即同一事件会被一组消费者其中之一消费

    /**
     * @param isMoreProducer 单生产者会有更好的性能
     * @param consumers
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> DisruptorQueue<T> getWorkPoolQueue(boolean isMoreProducer,
                                                         DisruptorHandler<T>... consumers) {
        Disruptor<Event<T>> _disruptor = new Disruptor<>(new DisruptorEventFactory<>(),
                1024 * 1024, Executors.defaultThreadFactory(),
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new SleepingWaitStrategy());
        _disruptor.handleEventsWithWorkerPool(consumers);
        // 启动disruptor
        return new DisruptorQueue<>(_disruptor);
    }

    // 创建"发布订阅模式"的操作队列，即同一事件会被多个消费者并行消费
    @SafeVarargs
    public static <T> DisruptorQueue<T> getHandleEventsQueue(boolean isMoreProducer,
                                                             DisruptorHandler<T>... consumers) {
        Disruptor<Event<T>> _disruptor = new Disruptor<>(Event::new,
                1024 * 1024, Executors.defaultThreadFactory(),
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new SleepingWaitStrategy());
        _disruptor.handleEventsWith(consumers);
        return new DisruptorQueue<>(_disruptor);
    }

    @SafeVarargs
    public static DisruptorQueue<String> getStringHandleEventsQueue(boolean isMoreProducer,
                                                                    DisruptorHandler<String>... consumers) {
        if (stringQueue == null) {
            Disruptor<Event<String>> _disruptor = new Disruptor<>(Event::new,
                    1024 * 1024, Executors.defaultThreadFactory(),
                    isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                    new SleepingWaitStrategy());
            _disruptor.handleEventsWith(consumers);
            stringQueue = new DisruptorQueue<>(_disruptor);
        }
        return stringQueue;
    }

    // 直接通过传入的 Disruptor 对象创建操作队列（如果消费者有依赖关系的话可以用此方法）
    public static <T> DisruptorQueue<T> getQueue(Disruptor<Event<T>> disruptor) {
        return new DisruptorQueue<>(disruptor);
    }
}
