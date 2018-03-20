package com.token01.thread;


import java.util.concurrent.Semaphore;

/**
 * SemaphoreTest
 * 信号量 不同步
 * 目的是为了解决多个线程并发同一资源造成的数据不一致的问题
 * 非公平锁性能高于公平锁性能的原因：
 * 在恢复一个被挂起的线程与该线程真正运行之间存在着严重的延迟。
 * 假设线程A持有一个锁，并且线程B请求这个锁。由于锁被A持有，因此B将被挂起。
 * 当A释放锁时，B将被唤醒，因此B会再次尝试获取这个锁。
 * 与此同时，如果线程C也请求这个锁，那么C很可能会在B被完全唤醒之前获得、使用以及释放这个锁。
 * 这样就是一种双赢的局面：B获得锁的时刻并没有推迟，C更早的获得了锁，并且吞吐量也提高了。
 * 当持有锁的时间相对较长或者请求锁的平均时间间隔较长，应该使用公平锁。
 * 在这些情况下，插队带来的吞吐量提升（当锁处于可用状态时，线程却还处于被唤醒的过程中）可能不会出现。
 * <p>
 * 2 获取许可
 * 可以使用acquire()、acquire(int)、tryAcquire()等去获取许可，其中int参数表示一次性要获取几个许可，
 * 默认为1个，acquire方法在没有许可的情况下，要获取许可的线程会阻塞，而tryAcquire()方法在没有许可的情况下会立即返回 false
 * ，要获取许可的线程不会阻塞，这与Lock类的lock()与tryLock()类似
 * <p>
 * 3 释放许可
 * 线程可调用 release()、release(int)来释放（归还）许可，注意一个线程调用release()之前并不要求一定要调用了acquire （
 * There is no requirement that a thread that releases a permit must have acquired that permit by calling {@link #acquire}）
 * <p>
 * 4 使用场景
 * 我们一般使用信号量来限制访问资源的线程数量，比如有一个食堂，最多允许5个人同时吃饭，则如下：
 *
 * @author sun-abel
 * @create 2018-03-20 下午7:12
 **/
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        new IncThread(semaphore, "A");
        new DecThread(semaphore, "B");
    }

    static class Shared {
        static int count = 0;
    }

    private static class IncThread implements Runnable {
        String name;
        Semaphore sem;

        public IncThread(Semaphore semaphore, String a) {
            sem = semaphore;
            name = a;
            new Thread(this).start();
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            System.out.println("Starting " + name);

            try {
                // 获取第一个许可证
                sem.acquire();
                System.out.println(name + "get a permit ");
                // 开始访问共享资源
                for (int i = 0; i < 5; i++) {
                    Shared.count++;
                    System.out.println(name + ":" + Shared.count);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "releases the permit");
            sem.release();
        }
    }


    private static class DecThread implements Runnable {
        String name;
        Semaphore sem;

        public DecThread(Semaphore semaphore, String b) {

            sem = semaphore;
            name = b;
            new Thread(this).start();
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            System.out.println("Starting " + name);

            try {
                // 获取第一个许可证
                sem.acquire();
                System.out.println(name + "get a permit ");
                // 开始访问共享资源
                for (int i = 0; i < 5; i++) {
                    Shared.count--;
                    System.out.println(name + ":" + Shared.count);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "releases the permit");
            sem.release();

        }
    }
}
