package cn.me.ppx.infrastructure.common.eventbus.jctools;

import org.jctools.queues.*;

import java.util.Queue;

/**
 * @author ym
 * @date 2022/11/10 18:14
 * SPSC-单一生产者单一消费者（有界和无界）
 * MPSC-多生产者单一消费者（有界和无界）
 * SPMC-单生产者多消费者（有界）
 * MPMC-多生产者多消费者（有界）
 */
public class Main {
    public static void main(String[] args) {
        // spsc-有界/无界队列
        Queue<String> spscArrayQueue = new SpscArrayQueue<>(16);
        Queue<String> spscUnboundedArrayQueue = new SpscUnboundedArrayQueue<>(2);
        // spmc-有界队列
        Queue<String> spmcArrayQueue = new SpmcArrayQueue<>(16);
        // mpsc-有界/无界队列
        Queue<String> mpscArrayQueue = new MpscArrayQueue<>(16);
        Queue<String> mpscChunkedArrayQueue = new MpscChunkedArrayQueue<>(1024, 8 * 1024);
        Queue<String> mpscUnboundedArrayQueue = new MpscUnboundedArrayQueue<>(2);
        // mpmc-有界队列
        Queue<String> mpmcArrayQueue = new MpmcArrayQueue<>(16);
    }
}
