package cn.me.ppx.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ym
 * @date 2022/11/7 10:34
 */
public class Main {
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));

    public void test() {
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            // submit不会抛异常
            executor.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    count.getAndIncrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        }
        System.out.println(count);
    }

    public void test2() throws ExecutionException, InterruptedException {
        Future<AtomicInteger> submit = executor.submit(() -> {
            AtomicInteger count = new AtomicInteger(0);
            List<CompletableFuture<Void>> list = new ArrayList<>(10);
            List<Integer> a = new ArrayList<>(10);
            AtomicInteger index = new AtomicInteger(0);
            for (int i = 0; i < 10; i++) {
                a.add(i);

//                list.add(CompletableFuture.runAsync(() -> g(index, a)));
                list.add(CompletableFuture.runAsync(() ->{
                    synchronized (a){
                        Integer integer = a.get(index.get());
                        index.getAndIncrement();
                        System.out.println(integer + Thread.currentThread().getName());
                    }
                }));
            }
            CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
            return count;
        });
        executor.shutdown();

        System.out.println(submit.get());
    }

    private synchronized void g(AtomicInteger atomicInteger, List<Integer> a) {
        Integer integer = a.get(atomicInteger.get());
        atomicInteger.getAndIncrement();
        System.out.println(integer + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Main main = new Main();
        main.test2();
    }
}
