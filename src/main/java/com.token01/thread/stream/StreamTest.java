package com.token01.thread.stream;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * streamTest
 * baseStream<T s extend baseStream></>
 * void close() 调用关闭程序
 * boolean isParallel() 并行流 返回true 顺序流
 * Iterator<T> iterator()  获取流的迭代器 并返回迭代器的引用</>
 * <p>
 * doubleStream
 * intStream
 * longStream
 *
 * @author sun-abel
 * @create 2018-03-23 下午3:04
 **/
public class StreamTest {
    public static void main(String[] args) {
        ArrayList<Integer> myList = new ArrayList<>();
        myList.add(1);
        myList.add(3);
        myList.add(21);
        myList.add(34);
        myList.add(5);
        System.out.println("list" + myList);
        Stream<Integer> myStream = myList.stream();
        Optional<Integer> minVal = myStream.min(Integer::compare);
        if (minVal.isPresent())
            System.out.println("minMum" + minVal.get());
        myStream = myList.stream();
        Optional<Integer> maxVal = myStream.max(Integer::compare);
        if (maxVal.isPresent())
            System.out.println("maxMum" + maxVal.get());
        Stream<Integer> sortedStream = myList.stream().sorted();
        System.out.println("sorted stream");
        sortedStream.forEachOrdered((n) -> System.out.println(n + " "));
        System.out.println();
        Stream<Integer> oddVals = myList.stream().sorted().filter((n) -> (n % 2) == 1);
        System.out.println("odd values");
        oddVals.forEach((n) -> System.out.println(n + ""));
    }
}
