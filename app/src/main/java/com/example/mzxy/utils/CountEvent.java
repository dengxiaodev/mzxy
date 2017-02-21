package com.example.mzxy.utils;

/**
 * Created by Administrator on 2017/2/12 0012.
 */

public class CountEvent {
    private int count;

    public CountEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountEvent{" +
                "count=" + count +
                '}';
    }
}
