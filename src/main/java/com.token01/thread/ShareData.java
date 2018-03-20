package com.token01.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程间的数据共享及static线程安全问题
 */
public class ShareData {


    /**
     * 创建一个静态线程池工厂类型
     */
    static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    static ExecutorService singleExecutor = new ThreadPoolExecutor(1, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

    private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();

    public static void main(String[] args) {
        MyTest myTest = new MyTest();

        for (int i = 0; i < 10; i++) {
            singleExecutor.execute(myTest);
        }
        singleExecutor.shutdown();

    }

    static class MyTest implements Runnable {
        int data = new Random().nextInt();
        @Override
        public void run() {
            try {
                //局部变量属于线程安全
                System.out.println("当前线程：" + Thread.currentThread().getName() + "  data:  " + data);
                threadData.put(Thread.currentThread(), data);
                new A().get();
                new B().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class A {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("A：" + Thread.currentThread().getName() + "  data:  " + data);
        }
    }

    static class B {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("B：" + Thread.currentThread().getName() + " data:  " + data);
        }
    }
}
