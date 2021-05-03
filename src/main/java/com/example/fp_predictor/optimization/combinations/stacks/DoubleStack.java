package com.example.fp_predictor.optimization.combinations.stacks;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;

public class DoubleStack implements Stackable {

    private final PlayerForecast player1;
    private final PlayerForecast player2;
    private double price;
    private double expectedPoints;

    public DoubleStack(PlayerForecast player1, PlayerForecast player2) {
        this.player1 = player1;
        this.player2 = player2;
        price = player1.getPrice() + player2.getPrice();
        expectedPoints = player1.getExpectedPoints() + player2.getExpectedPoints();
    }

    public PlayerForecast getPlayer1() {
        return player1;
    }

    public PlayerForecast getPlayer2() {
        return player2;
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
                "player1=" + player1 +
                ", player2=" + player2 +
                ", price=" + price +
                ", expectedPoints=" + expectedPoints +
                '}';
    }
}
