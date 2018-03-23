package com.token01.thread.Executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 返回一个包含指定数目线程的线程池
 * ，如果任务数量多于线程数目，那么没有没有执行的任务必须等待，直到有任务完成为止。
 * @author abel-sun 2016/4/25.
 */
public class TaskExwcutionWebServer {

    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) {
        while (true){
            Runnable task = new Runnable() {
                public void run() {
                    System.out.println("llll");
                }
            };
            exec.execute(task);
        }
    }
}
