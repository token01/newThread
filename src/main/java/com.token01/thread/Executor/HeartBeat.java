package com.token01.thread.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用newScheduledThreadPool来模拟心跳机制
 * 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。 任务调度
 *
 * @author abel-sun 2016/4/26.
 * <p>
 * public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit)
 * //向定时任务线程池提交一个延时Runnable任务（仅执行一次）
 * public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);
 * //向定时任务线程池提交一个延时的Callable任务（仅执行一次）
 * public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay,
 * long period, TimeUnit unit)
 * //向定时任务线程池提交一个固定时间间隔执行的任务
 * public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay,
 * long delay, TimeUnit unit);
 * //向定时任务线程池提交一个固定延时间隔执行的任务
 */
public class HeartBeat {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("HeartBeat.........................");
            }
        };
        executor.scheduleAtFixedRate(task, 5, 3, TimeUnit.SECONDS);
    }
}
