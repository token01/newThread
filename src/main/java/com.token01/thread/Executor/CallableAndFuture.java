package com.token01.thread.Executor;

import java.util.concurrent.*;

/**
 * 执行完线程后返回结果 异步处理
 * V get() throws InterruptedException, ExecutionException;
 * 超时处理
 * V get(long timeout, TimeUnit unit)
 * @author abel-sun
 */
public class CallableAndFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "token01";
            }
        });
        try {
            System.out.println("任务的执行结果："+future.get(1001,TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
