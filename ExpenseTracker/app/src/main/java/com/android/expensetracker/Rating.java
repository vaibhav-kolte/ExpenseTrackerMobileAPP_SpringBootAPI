package com.android.expensetracker;

public class Rating {
    private float rate;
    private int count;

    public Rating(float rate, int count) {
        this.rate = rate;
        this.count = count;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
