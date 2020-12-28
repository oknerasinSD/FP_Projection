package com.example.fp_predictor.analysis.xg_difference;

public class DataInstance {


    private int position;
    private double xG_difference;
    private int numberOfGoals;

    public DataInstance(int position, double xG_difference, int numberOfGoals) {
        this.position = position;
        this.xG_difference = xG_difference;
        this.numberOfGoals = numberOfGoals;
    }

    @Override
    public String toString() {
        return "StatsInstance{" +
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

    public int getNumberOfGoals() {
        return numberOfGoals;
    }
}
