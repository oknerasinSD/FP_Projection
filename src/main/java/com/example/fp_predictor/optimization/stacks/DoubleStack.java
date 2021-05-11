package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.domain.Player;

/**
 * Класс для представления дабл-стеков.
 */
public class DoubleStack implements Stackable {

    private final Player[] players = new Player[2];

    private int price_x_10;

    private double expectedPoints;

    private String team;

    public DoubleStack(Player player1, Player player2) {
        players[0] = player1;
        players[1] = player2;
        price_x_10 = player1.getPrice_x_10() + player2.getPrice_x_10();
        expectedPoints = player1.getExpectedPoints() + player2.getExpectedPoints();
        team = player1.getTeam();
    }

    public Player getPlayer1() {
        return players[0];
    }

    public Player getPlayer2() {
        return players[1];
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
        return expectedPoints / price_x_10;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public int getPrice_x_10() {
        return price_x_10;
    }

    @Override
    public double getExpectedPoints() {
        return 0;
    }

    @Override
    public int getNumberOfPlayers() {
        return 2;
    }

    @Override
    public String toString() {
        return players[0] + " " + players[1];
    }
}
