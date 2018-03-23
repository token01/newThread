package com.token01.thread.MonitorThread;

/**
 * @author abel-sun
 */
public class MonitorTest {
    public static void main(String[] args) throws InterruptedException {
        MonitorManager monitor = new MonitorManager();
        monitor.startMonitor();
        Thread.sleep(500000);
    }
}
