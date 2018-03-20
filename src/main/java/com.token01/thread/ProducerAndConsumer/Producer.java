package com.token01.thread.ProducerAndConsumer;

/**
 * Created by token01 on 2016/8/18.
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
