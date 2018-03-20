package com.token01.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by token01
 * 一个ThreadLocal代表一个变量，所以其中只能放一个数据。
 * 当你有多个变量需要共享时就应该将定义一个对象来包装这些变量。
 * 在ThreadLocal对象中存储这个对象
 *
 * @author abel-sun
 */
public class ThreadLocalTestImprove {

    static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    static  ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {


        MyTest myTest =new MyTest();
        for (int i = 0; i < 2; i++) {
        singleThreadPool.execute(myTest);

        }
    }

    static class MyTest implements Runnable {

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
            try {
                int data = new Random().nextInt();
                MyThreadLocal myThreadLocal = MyThreadLocal.getInstance();
                System.out.println("线程:" + Thread.currentThread().getName() + "data  " + data);
                myThreadLocal.setName("token01" + data);
                myThreadLocal.setAge(data);
                new A().get();
                new B().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

    static class A {
        public void get() {
            MyThreadLocal AThreadLocal = MyThreadLocal.getInstance();
            System.out.println("A线程" + Thread.currentThread().getName() + "的name" + AThreadLocal.getName() + "    age" + AThreadLocal.getAge());
        }
    }

    static class B {
        public void get() {
            MyThreadLocal BThreadLocal = MyThreadLocal.getInstance();
            System.out.println("B线程" + Thread.currentThread().getName() + "的name" + BThreadLocal.getName() + "    age" + BThreadLocal.getAge());
        }
    }

    static class MyThreadLocal {
        /**
         * 禁止外部直接创建对象
         */
        private MyThreadLocal() {
        }

        private static ThreadLocal<MyThreadLocal> mapThreadLocal = new ThreadLocal<MyThreadLocal>();

        private static MyThreadLocal getInstance() {
            MyThreadLocal myThreadLocal = mapThreadLocal.get();
            if (myThreadLocal == null) {
                myThreadLocal = new MyThreadLocal();
                mapThreadLocal.set(myThreadLocal);
            }
            return myThreadLocal;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
