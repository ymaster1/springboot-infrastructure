package cn.me.ppx.infrastructure.common.eventbus.disruptor;

import cn.me.ppx.infrastructure.common.eventbus.Event;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.List;

/**
 * @author ym
 * @date 2022/11/10 14:21
 */
public class DisruptorQueue<T> {
    private Disruptor<Event<T>> disruptor;
    private RingBuffer<Event<T>> ringBuffer;
    /**
     * 3.0之后提供的新发布方法
     */
    private final EventTranslatorOneArg<Event<T>, T> TRANSLATOR =
            (event, sequence, bb) -> event.setData(bb);

    public DisruptorQueue(Disruptor<Event<T>> disruptor) {
        this.disruptor = disruptor;
        this.ringBuffer = disruptor.getRingBuffer();
        this.disruptor.start();
    }

    public void add(T t) {
        if (t != null) {
            // 请求下一个事件序号
            long sequence = this.ringBuffer.next();

            try {
                Event<T> event = this.ringBuffer.get(sequence);
                // 给事件设置内容
                event.setData(t);
            } finally {
                // 发布事件，必须再finally中调用，如果未调用会阻塞后续的发布
                this.ringBuffer.publish(sequence);

            }
        }
    }

    public void addAll(List<T> ts) {
        if (ts != null) {

            for (T t : ts) {
                if (t != null) {
//                    this.add(t);
                    this.ringBuffer.publishEvent(TRANSLATOR, t);
                }
            }
        }
    }

    public long cursor() {
        return this.disruptor.getRingBuffer().getCursor();
    }

    public void shutdown() {
        this.disruptor.shutdown();
    }

    public Disruptor<Event<T>> getDisruptor() {
        return this.disruptor;
    }

    public void setDisruptor(Disruptor<Event<T>> disruptor) {
        this.disruptor = disruptor;
    }

    public RingBuffer<Event<T>> getRingBuffer() {
        return this.ringBuffer;
    }

    public void setRingBuffer(RingBuffer<Event<T>> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
}