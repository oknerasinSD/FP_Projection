package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;
import com.example.fp_predictor.domain.Player;

/**
 * Класс для представления трипл-стеков.
 */
public class TripleStack implements Stackable {

    private final Player[] players = new Player[3];

    private double price;

    private double expectedPoints;

    private String team;

    public TripleStack(Player player1, Player player2, Player player3) {
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        this.price = player1.getPrice() + player2.getPrice() + player3.getPrice();
        this.expectedPoints = player1.getExpectedPoints() + player2.getExpectedPoints() + player3.getExpectedPoints();
        team = player1.getTeam();
    }

    public Player getPlayer1() {
        return players[0];
    }

    public Player getPlayer2() {
        return players[1];
    }

    public Player getPlayer3() {
        return players[2];
    }

    @Override
    public String getTeam() {
        return team;
    }

    @Override
    public Boolean isInSameTeam(Stackable stackable) {
        return team.equals(stackable.getTeam());
    }

    @Override
    public double getValue() {
        return expectedPoints / price;
    }

    @Override
    public Player[] getPlayers() {
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
    public int getNumberOfPlayers() {
        return 3;
    }

    @Override
    public String toString() {
        return players[0] + " " + players[1] + " " + players[2];
    }
}
