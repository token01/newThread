package com.token01.thread.Executor;

import java.util.concurrent.*;

/**
 * 执行完线程后返回结果
 */
public class CallableAndFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
            public String call() throws Exception {
                return "token01";
            }
        });
        System.out.println("任务的执行结果："+future.get());
    }
}
