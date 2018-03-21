package com.token01.thread.Long;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicLong
 * 在单线程的情况下，AtomicLong稍微快一点，在2个线程的时候
 * AtomicLong比LongAdder慢近4倍，
 * 而在线程数和机器CPU核数一致的情况下，AtomicLong比LongAdder慢近5倍。
 * <p>
 * 强调下，我们知道，AtomicLong的实现方式是内部有个value 变量，当多线程并发自增，
 * 自减时，均通过CAS 指令从机器指令级别操作保证并发的原子性。
 *
 * @author sun-abel
 * @create 2018-03-21 下午3:14
 **/
public class AtomicLongTest {
    public static final int MAX_THREAD_COUNT = 10;
    private static final int TASK_COUNT = 10;
    private static final int TARGET_COUNT = 100000000;

    private AtomicLong atomicLong = new AtomicLong(0);
    private LongAdder adder = new LongAdder();

    private static CountDownLatch latchForAtomicLong = new CountDownLatch(TASK_COUNT);
    private static CountDownLatch latchForAddr = new CountDownLatch(TASK_COUNT);

    /**
     * 实现线程atomicLong 线程
     */
    private class AtomicLongThread implements Runnable {

        protected String name;
        protected long startTime;

        public AtomicLongThread(long startTime) {
            this.startTime = startTime;
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
            long v = atomicLong.get();
            while (v < TARGET_COUNT) {
                v = atomicLong.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("AtomicLongThread Spend:" + (endTime - startTime) + "ms, v = " + v);
            latchForAtomicLong.countDown();

        }
    }

    public class LongAddderThread implements Runnable {

        protected String name;
        protected long startTime;

        public LongAddderThread(long startTime) {
            this.startTime = startTime;
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
            long v = adder.sum();
            while (v < TARGET_COUNT) {
                adder.increment();
                v = adder.sum();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("LongAddderThread Spend:" + (endTime - startTime) + "ms, v = " + v);
            latchForAddr.countDown();
        }
    }

    public void testAtomicLongThread() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        AtomicLongThread atomicIntegerThread = new AtomicLongThread(startTime);
        for (int i = 0; i < TASK_COUNT; i++) {
            exe.submit(atomicIntegerThread);
        }
        latchForAtomicLong.await();
        exe.shutdown();
    }

    public void testLongAdderThread() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        LongAddderThread longAddderThread = new LongAddderThread(startTime);
        for (int i = 0; i < TASK_COUNT; i++) {
            exe.submit(longAddderThread);
        }
        latchForAddr.await();
        exe.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * MAX_THREAD_COUNT、TASK_COUNT的值都为3时候测试结果如下：
         */
        AtomicLongTest test = new AtomicLongTest();
        test.testAtomicLongThread();
        test.testLongAdderThread();

        /**
         * MAX_THREAD_COUNT、TASK_COUNT的值都为10时候测试结果如下：
         */
//        AtomicLongTest test1 = new AtomicLongTest();
//        test1.testAtomicLongThread();
//        test1.testLongAdderThread();


    }
}
