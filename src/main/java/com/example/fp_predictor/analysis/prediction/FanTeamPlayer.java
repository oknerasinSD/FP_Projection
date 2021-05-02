package com.example.fp_predictor.analysis.prediction;

/**
 * Данные об игроке от фэнтези-провайдера.
 */
public class FanTeamPlayer {

    /** Имя. */
    private final String name;

    /** Фамилия. */
    private final String surname;

    /** Команда. */
    private final String team;

    /** Позиция. */
    private final String position;

    /** Цена. */
    private final double price;

    /** Ожидаемые очки. */
    private double expectedPoints;

    public FanTeamPlayer(String name, String surname, String team, String position, double price) {
        this.name = name;
        this.surname = surname;
        this.team = team;
        this.position = position;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTeam() {
        return team;
    }

    public String getPosition() {
        return position;
    }

    public double getPrice() {
        return price;
    }

    public void setExpectedPoints(double expectedPoints) {
        this.expectedPoints = expectedPoints;
    }

    @Override
    public String toString() {
        return "FanTeamPlayer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", team='" + team + '\'' +
                ", position='" + position + '\'' +
                ", price=" + price +
                ", expectedPoints=" + expectedPoints +
                '}';
    }
}
