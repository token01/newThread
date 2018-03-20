package com.token01.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchTest2
 * 计数器
 *
 * @author sun-abel
 * @create 2018-03-20 下午7:31
 **/
public class CountDownLatchTest2 {
    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(5);
        System.out.println("starting thread");
        new MyThread(count);

        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static class MyThread implements Runnable {
        CountDownLatch latch;
        public MyThread(CountDownLatch count) {
            latch =count;
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
            for (int i = 0; i <5 ; i++) {
                System.out.println(i);
                // 减量
                latch.countDown();
            }
        }
    }
}
