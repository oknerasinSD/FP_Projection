package com.example.fp_predictor.analysis.prediction;

/**
 * Результат прогнозирования для подачи информации на дальнейшую обработку.
 */
public class ResultInstance {

    /** Игрок */
    private String name;

    /** Команда */
    private String team;

    /** Позиция */
    private String position;

    /** Ожидаемые очки */
    private double expectedPoints;

    public ResultInstance(String name, Double expectedPoints) {

    }

    public ResultInstance(String name, String team, String position, double expectedPoints) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.expectedPoints = expectedPoints;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getPosition() {
        return position;
    }

    public double getExpectedPoints() {
        return expectedPoints;
    }
}
