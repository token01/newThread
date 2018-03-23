package com.token01.thread.stream;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * StreamTest3
 *
 * @author sun-abel
 * @create 2018-03-23 下午3:39
 **/
public class StreamTest3 {
    public static void main(String[] args) {
        ArrayList<Double> myList = new ArrayList<>();
        myList.add(1.0);
        myList.add(3.0);
        myList.add(21.0);
        myList.add(34.0);
        myList.add(5.0);

        Stream<Double> stream =myList.stream().map((a) -> Math.sqrt(a));
        double productOfSqrRoots = stream.reduce(1.0,(a,b)->a*b);
        System.out.println("product of sqr roots"+productOfSqrRoots);
    }
}
