package cn.me.ppx.infrastructure.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author ym
 * @date 2022/10/31 9:54
 */
public class MDCThreadPoolExecutor extends ThreadPoolExecutor {

    private static final Logger LOGGER= LoggerFactory.getLogger(MDCThreadPoolExecutor.class);

    public MDCThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(final Runnable runnable) {
        // 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
        final Map<String, String> context = MDC.getCopyOfContextMap();
        super.execute(new Runnable() {
            @Override
            public void run() {
                // 将父线程的MDC内容传给子线程
                MDC.setContextMap(context);
                try {
                    // 执行异步操作
                    runnable.run();
                } finally {
                    // 清空MDC内容
                    MDC.clear();
                }
            }
        });
    }
}
