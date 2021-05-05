package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;

public class TripleStack implements Stackable {

    private final PlayerForecast[] players = new PlayerForecast[3];
    private double price;
    private double expectedPoints;

    public TripleStack(PlayerForecast player1, PlayerForecast player2, PlayerForecast player3) {
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        this.price = player1.getPrice() + player2.getPrice() + player3.getPrice();
        this.expectedPoints = player1.getExpectedPoints() + player2.getExpectedPoints() + player3.getExpectedPoints();
    }

    public PlayerForecast getPlayer1() {
        return players[0];
    }

    public PlayerForecast getPlayer2() {
        return players[1];
    }

    public PlayerForecast getPlayer3() {
        return players[2];
    }

    @Override
    public PlayerForecast[] getPlayers() {
        return players;
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
                "player1=" + players[0] +
                ", player2=" + players[1] +
                ", player3=" + players[2] +
                ", price=" + price +
                ", expectedPoints=" + expectedPoints +
                '}';
    }
}
