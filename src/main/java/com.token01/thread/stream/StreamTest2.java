package com.token01.thread.stream;

import java.util.ArrayList;

/**
 * StreamTest2
 *
 * @author sun-abel
 * @create 2018-03-23 下午3:31
 **/
public class StreamTest2 {

    public static void main(String[] args) {
        ArrayList<Double> myList = new ArrayList<>();
        myList.add(1.0);
        myList.add(3.0);
        myList.add(21.0);
        myList.add(34.0);
        myList.add(5.0);

        double produceOfSqrRoots = myList.parallelStream().reduce(
                1.0,
                (a, b) -> a * Math.sqrt(b),
                (a, b) -> a * b
        );
        System.out.println("product of square roots"+produceOfSqrRoots);
    }
}