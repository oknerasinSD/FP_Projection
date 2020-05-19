package com.example.fp_predictor.analysis.prediction;

public class PredictionInstance {
    private String playerName;
    private double expectedPoints;

    public PredictionInstance(String playerName, double expectedPoints) {
        this.playerName = playerName;
        this.expectedPoints = expectedPoints;
    }

    @Override
    public String toString() {
        return "PredictionInstance{" +
                "playerName='" + playerName + '\'' +
                ", expectedPoints=" + expectedPoints +
                '}';
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getExpectedPoints() {
        return expectedPoints;
    }
}
