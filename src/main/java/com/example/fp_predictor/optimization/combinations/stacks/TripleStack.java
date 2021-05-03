package com.example.fp_predictor.optimization.combinations.stacks;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;

public class TripleStack implements Stackable {

    private final PlayerForecast player1;
    private final PlayerForecast player2;
    private final PlayerForecast player3;
    private double price;
    private double expectedPoints;

    public TripleStack(PlayerForecast player1, PlayerForecast player2, PlayerForecast player3) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.price = player1.getPrice() + player2.getPrice() + player3.getPrice();
        this.expectedPoints = player1.getExpectedPoints() + player2.getExpectedPoints() + player3.getExpectedPoints();
    }

    public PlayerForecast getPlayer1() {
        return player1;
    }

    public PlayerForecast getPlayer2() {
        return player2;
    }

    public PlayerForecast getPlayer3() {
        return player3;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getExpectedPoints() {
        return expectedPoints;
    }

    @Override
    public String toString() {
        return "TripleStack{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", player3=" + player3 +
                ", price=" + price +
                ", expectedPoints=" + expectedPoints +
                '}';
    }
}
