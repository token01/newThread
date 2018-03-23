package com.token01.thread.ProducerAndConsumer;

/**
 * @author abel-sun 2016/8/18.
 */
public class Producer implements Runnable{
    private EventStorage storage;
    public Producer(EventStorage storage){
        this.storage = storage;
    }
    @Override
    public void run() {
        for(int i = 0; i < 100; i ++){
            storage.set();
        }
    }
}
