package com.xyz.study.common.jike.daily;

/**
 * @author Zhu WeiJie
 * @date 2021/6/22
 **/
public class ForLoopTest {

    private static int[] numbers = {1, 6, 8};

    public static void main(String[] args) {
        MovingAverage ma = new MovingAverage();
        for (int number : numbers) {
            ma.submit(number);
        }
        double avg = ma.getAvg();
    }

}