package com.token01.thread;

/**
 * Created by token01 on 2016/4/4.
 *
 * 静态类成员变量虽然可以在不同的线程之间共享，
 * 但是，根据Java规范定义的内存模型，
 * 在本例中，只有当main线程的写操作(ready=true)在ReaderThread的读操作(while(!ready))之前执行，
 *
 */
public class NoVisibility {
    private static  boolean ready;
    private static int number;

    private static class ReaderThread extends  Thread{
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
