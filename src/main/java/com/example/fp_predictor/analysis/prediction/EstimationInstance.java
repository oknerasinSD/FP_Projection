package com.example.fp_predictor.analysis.prediction;

public class EstimationInstance extends ResultInstance {

    private double resultPoints;
    private double difference;

    public EstimationInstance(String playerName, double expectedPoints, double resultPoints) {
        super(playerName, expectedPoints);
        this.resultPoints = resultPoints;
    }

    public double getResultPoints() {
        return resultPoints;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    @Override
    public String toString() {
        return "EstimationInstance{" +
                "playerName=" + getName() +
                ", expectedPoints=" + getExpectedPoints() +
                ", resultPoints=" + resultPoints +
                ", difference=" + difference +
                '}';
    }
}
