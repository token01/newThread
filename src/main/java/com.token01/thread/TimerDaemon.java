package com.token01.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 守护线程与非守护线程
 * 时间守护线程
 * <p>
 * Java分为两种线程：用户线程和守护线程
 * <p>
 * (一)所谓守护线程是指在程序运行的时候在后台提供一种通用服务的线程，
 * 比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。
 * 因此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。
 * 反过来说，只要任何非守护线程还在运行，程序就不会终止。
 * <p>
 * (二)守护线程和用户线程的没啥本质的区别：
 * 唯一的不同之处就在于虚拟机的离开：
 * 如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。
 * 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
 *
 * @author abel-sun
 */
public class TimerDaemon {
    private static Timer timer = new Timer(true); //定时器为守护线程

    static public class MyTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("运行时间为：" + new Date());
        }
    }

    public static void main(String[] args) {
        //创建定时任务
        MyTask myTask = new MyTask();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "2018-02-20 22:17:10";
        try {
            Date date = sdf.parse(dateString);
            System.out.println("任务计划启动时间：" + date + "  当前时间：" + new Date());
            timer.schedule(myTask, date);
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
