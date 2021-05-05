package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;

public class DoubleStack implements Stackable {

    private final PlayerForecast[] players = new PlayerForecast[2];
    private double price;
    private double expectedPoints;

    public DoubleStack(PlayerForecast player1, PlayerForecast player2) {
        players[0] = player1;
        players[1] = player2;
        price = player1.getPrice() + player2.getPrice();
        expectedPoints = player1.getExpectedPoints() + player2.getExpectedPoints();
    }

    public PlayerForecast getPlayer1() {
        return players[0];
    }

    public PlayerForecast getPlayer2() {
        return players[1];
    }

    @Override
    public PlayerForecast[] getPlayers() {
        return players;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public double getExpectedPoints() {
        return 0;
    }

    @Override
    public String toString() {
        return "DoubleStack{" +
                "player1=" + players[1] +
                ", player2=" + players[2] +
                ", price=" + price +
                ", expectedPoints=" + expectedPoints +
                '}';
    }
}
