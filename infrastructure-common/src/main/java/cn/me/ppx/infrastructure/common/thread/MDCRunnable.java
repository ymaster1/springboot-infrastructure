package cn.me.ppx.infrastructure.common.thread;

import org.slf4j.MDC;

import java.util.Map;

/**
 * @author ym
 * @date 2022/10/31 9:52
 */
public abstract class MDCRunnable {
    private final Runnable runnable;
    /**
     * 保存当前主线程的MDC值
     */
    private final Map<String, String> mainMdcMap;

    public MDCRunnable(Runnable runnable) {
        this.runnable = runnable;
        this.mainMdcMap = MDC.getCopyOfContextMap();
    }
    public void run() {
        // 将父线程的MDC值赋给子线程
        for (Map.Entry<String, String> entry : mainMdcMap.entrySet()) {
            MDC.put(entry.getKey(), entry.getValue());
        }
        // 执行被装饰的线程run方法
        runnable.run();
        // 执行结束移除MDC值
        for (Map.Entry<String, String> entry : mainMdcMap.entrySet()) {
            MDC.put(entry.getKey(), entry.getValue());
        }
    }

}
