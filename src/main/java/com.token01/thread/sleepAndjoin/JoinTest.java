package com.token01.thread.sleepAndjoin;

/**
 * join()
 *
 * @author abel-sun
 * 让主线程等待子线程结束之后才能继续运行。
 * a. join() 和 join(long millis, int nanos) 最后都调用了 join(long millis)。
 * b. 带参数的 join() 都为 synchronized method。
 * c. join() 调用了 join(0)，从源码可以看到 join(0) 不断检查线程（join() 所属的线程实例，非调用线程）是否是 Active。
 * <p>
 * 对网上其他分析 join() 的文章提出疑问
 * 网上有很多文章的描述我觉得有歧义，下面挑选一些描述进行分析，也欢迎大家留言一起讨论。
 * <p>
 * <p>
 * <p>
 * a. 子线程结束之后，"会唤醒主线程"，主线程重新获取cpu执行权，继续运行。
 * <p>
 * “唤醒”令人误解。其实是主线程调用方法不断去检查子线程的状态， 这是个主动的动作，而不是子线程去唤醒主线程。
 * <p>
 * <p>
 * <p>
 * b. join() 将几个并行线程的线程"合并为一个单线程"执行。
 * <p>
 * 我理解提这个说法的人的意思。但是这样描述只会让读者更难理解。
 * <p>
 * 在调用 join() 方法的程序中，原来的多个线程仍然多个线程，并没有发生“合并为一个单线程”。真正发生的是调用 join() 的线程进入 TIMED_WAITING 状态，等待 join() 所属线程运行结束后再继续运行。
 */

public class JoinTest {

    public static void main(String[] args) {

        ThreadB b = new ThreadB();

        ThreadA a = new ThreadA(b);

        a.start();
        ThreadC c = new ThreadC(b);
        c.start();   //threadB.bService();方法能被立即调用，说明join方法具有释放锁的特点
    }

    static class ThreadA extends Thread {
        private ThreadB b;

        public ThreadA(ThreadB b) {
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (b) {
                try {
                    b.start();
                    b.join(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("b run begin timer= " + System.currentTimeMillis());
                Thread.sleep(6000);
                System.out.println("b run end timer=  " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized public void bService() {
            System.out.println("打印了bservice timer=  " + System.currentTimeMillis());
        }
    }

    static class ThreadC extends Thread {
        private ThreadB threadB;

        public ThreadC(ThreadB threadB) {
            this.threadB = threadB;
        }

        @Override
        public void run() {
            threadB.bService();
        }
    }

}
