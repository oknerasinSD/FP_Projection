package com.example.fp_predictor.analysis.prediction;

/**
 * Экземпляр данных, необходимых для сравнения количества ожидаемых голов с фактическим.
 */
public class ComparisonResultInstance extends ResultInstance {

    /** Фактическое количество очков. */
    private double resultPoints;

    /** Разница. */
    private double difference;

    public ComparisonResultInstance(String playerName, double expectedPoints, double resultPoints) {
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
