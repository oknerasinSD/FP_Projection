package com.example.fp_predictor.analysis.xg_difference;

public class DataInstance {


    private int position;
    private double xG_difference;

    public DataInstance(int position, double xG_difference) {
        this.position = position;
        this.xG_difference = xG_difference;
    }

    @Override
    public String toString() {
        return "DataInstance{" +
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
