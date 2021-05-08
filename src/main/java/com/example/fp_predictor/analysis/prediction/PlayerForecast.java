package com.example.fp_predictor.analysis.prediction;

/**
 * Результат прогнозирования для подачи информации на дальнейшую обработку.
 */
public class PlayerForecast {

    /** ID игрока в системе FanTeam. */
    private int id;

    /** Игрок. */
    private String name;

    /** Команда. */
    private String team;

    /** Позиция игрока на поле. */
    private String position;

    /** Ожидаемые фэнтези-очки. */
    private double expectedPoints;

    /** Цена на игрока в текущем турнире. */
    private double price;

    public PlayerForecast(String name, Double expectedPoints) {

    }

    public PlayerForecast() {
    }

    public PlayerForecast(int id, String name, String team, String position, double expectedPoints, double price) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.position = position;
        this.expectedPoints = expectedPoints;
        this.price = price;
    }

    public int getId() {
        return id;
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " " + price + " " + team + "\n";
    }
}
