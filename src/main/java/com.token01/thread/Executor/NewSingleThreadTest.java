package com.token01.thread.Executor;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newSingleThreadTest
 * 返回以个包含单线程的Executor,将多个任务交给此Exector时，这个线程处理完一个任务后接着处理下一个任务，若该线程出现异常，将会有一个新的线程来替代。
 *
 * @author sun-abel
 * @create 2018-03-23 下午7:19
 **/
public class NewSingleThreadTest implements Runnable {
    public String name;

    public NewSingleThreadTest(String name) {
        this.name = name;
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
        int temp = 0;
        int i = 0;
        Random random = new Random();
        while (true) {
            int j = random.nextInt(100);

            System.err.println("temp----------->" + temp + "  i---------------->" + (i++) + "    j------------------>" + j);
            try {
                if (temp == 0 && j > 90) temp = 7 / 0;
                Thread.currentThread().sleep(10);
            } catch (Exception e) {
                e.printStackTrace();

                temp = 1;

            }
        }
    }

    public static void foo(NewSingleThreadTest threadTest) {
        threadTest = new NewSingleThreadTest("fife");
    }

    public static void main(String[] args) {
        NewSingleThreadTest newSingleThreadTest = new NewSingleThreadTest("max");
        foo(newSingleThreadTest);
        System.out.println(" aDog.name.equals Max ＝ " + newSingleThreadTest.name.equals("Max"));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // execute 不会有返回结果  submit 异步切返回结果数据的

        executorService.execute(newSingleThreadTest);


    }
}

