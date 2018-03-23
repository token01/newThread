package com.token01.thread.Executor;

import java.util.concurrent.*;

/**
 * corePoolSize：
 * 线程池启动后，在池中保持的线程的最小数量。需要说明的是线程数量是逐步到达corePoolSize值的。例如corePoolSize被设置为10，而任务数量只有5，则线程池中最多会启动5个线程，而不是一次性地启动10个线程。
 * maxinumPoolSize：
 * 线程池中能容纳的最大线程数量，如果超出，则使用RejectedExecutionHandler拒绝策略处理。
 * keepAliveTime：
 * 线程的最大生命周期。这里的生命周期有两个约束条件：一：该参数针对的是超过corePoolSize数量的线程；二：处于非运行状态的线程。举个例子：如果corePoolSize（最小线程数）为10，maxinumPoolSize（最大线程数）为20，而此时线程池中有15个线程在运行，过了一段时间后，其中有3个线程处于等待状态的时间超过keepAliveTime指定的时间，则结束这3个线程，此时线程池中则还有12个线程正在运行。
 * unit：
 * 这是keepAliveTime的时间单位，可以是纳秒，毫秒，秒，分钟等。
 * workQueue：
 * 任务队列。当线程池中的线程都处于运行状态，而此时任务数量继续增加，则需要一个容器来容纳这些任务，这就是任务队列。这个任务队列是一个阻塞式的单端队列。
 * <p>
 * threadFactory：
 * 定义如何启动一个线程，可以设置线程的名称，并且可以确定是否是后台线程等。
 * handler：
 *
 * @author abel-sun 2017/8/7.
 */
public class ThreadPoolDeadLockAvoidance {
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            1,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public void test(final String mes) {
        Runnable taskA = new Runnable() {
            @Override
            public void run() {
                System.out.println("Executing taskA");
                Runnable tabkB = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("TaskB processes " + mes);
                    }
                };
                Future<?> rs = threadPoolExecutor.submit(tabkB);
                try {
                    rs.get();   //等待taskB执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("TaskA Done");
            }
        };
        threadPoolExecutor.submit(taskA);
    }

    public static void main(String[] args) {
        ThreadPoolDeadLockAvoidance poolDeadLockAvoidance = new ThreadPoolDeadLockAvoidance();
        poolDeadLockAvoidance.test("token01");
    }
}
