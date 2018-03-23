package com.token01.thread;

import java.util.Random;

/**
 * 总之，ThreadLocal不是用来解决对象共享访问问题的，而主要是提供了保持对象的方法和避免参数传递的方便的对象访问方式。归纳了两点：
 1。每个线程中都有一个自己的ThreadLocalMap类对象，可以将线程自己的对象保持到其中，各管各的，线程可以正确的访问到自己的对象。
 2。将一个共用的ThreadLocal静态实例作为key，将不同对象的引用保存到不同线程的ThreadLocalMap中，然后在线程执行的各处通过这个静态ThreadLocal实例的get()方法取得自己线程保存的那个对象，避免了将这个对象作为参数传递的麻烦。
 * Created by token01 on 2016/3/5.
 */
public class ThreadLocalTest {

    private static ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    threadLocal.set(data);
                    System.out.println(Thread.currentThread().getName() + "--data:  " + data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            int data = (int) threadLocal.get();
            System.out.println("A线程 :  " + Thread.currentThread().getName() + "   data:  " + data);
        }
    }

    static class B {
        public void get() {
            int data = (int) threadLocal.get();
            System.out.println("B线程 :  " + Thread.currentThread().getName() + "   data:  " + data);
        }
    }
}
