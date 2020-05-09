package com.example.mvc1.analysis;

public class DataInstance1 {


    private int position;
    private double xG_difference;

    public DataInstance1(int position, double xG_difference) {
        this.position = position;
        this.xG_difference = xG_difference;
    }

    @Override
    public String toString() {
        return "DataInstance1{" +
                "position=" + position +
                ", xG_difference=" + xG_difference +
                '}';
    }

    public int getPosition() {
        return position;
    }

    public double getxG_difference() {
        return xG_difference;
    }
}
