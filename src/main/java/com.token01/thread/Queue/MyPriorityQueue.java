package com.token01.thread.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/** PriorityQueue  创建一个完整的二叉树
 * 优先队列
 * @author abel-sun
 *
 *  ①优先队列中不能存放空元素。
    ②压入元素后如果数组的大小不够会进行扩充，上面的queue其实就是一个默认初始值为11的数组（也可以赋初始值）。
    ③offer元素的主要调整逻辑在 siftUp ( i, e )函数中。下面看看 siftUp(i, e) 函数到底是怎样实现的。
 */
public class MyPriorityQueue {
    public static void main(String[] args) {
        PriorityQueue queue = new PriorityQueue(5, new NumComparator());
        queue.add(5);
        queue.add(1);
        queue.add(4);
        queue.add(4);
        queue.add(3);
        Arrays.asList();
        while (queue.size() > 0){
            System.out.println(queue.remove());
        }
    }

    static class NumComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return  -1;
            } else
            return 0;
        }
    }
}
