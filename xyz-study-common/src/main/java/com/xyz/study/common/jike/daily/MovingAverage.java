package com.xyz.study.common.jike.daily;

/**
 * @author Zhu WeiJie
 * @date 2021/6/22
 **/
public class MovingAverage {

    private int count = 0;
    private double sum = 0.0D;

    public void submit(double value) {
        this.count++;
        this.sum += value;
    }

    public double getAvg() {
        if (0 == this.count) {
            return sum;
        }
        return this.sum / this.count;
    }

}
