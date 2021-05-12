package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.domain.Player;

/**
 * Класс для представления трипл-стеков.
 */
public class TripleStack implements Stackable {

    private final Player[] players = new Player[3];

    private int price_x_10;

    private double expectedPoints;

    private String team;

    public TripleStack(Player player1, Player player2, Player player3) {
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        this.price_x_10 = player1.getPrice_x_10() + player2.getPrice_x_10() + player3.getPrice_x_10();
        this.expectedPoints = player1.getExpectedPoints() + player2.getExpectedPoints() + player3.getExpectedPoints();
        team = player1.getTeam();
        checkDefensiveStackingFine();
    }

    private void checkDefensiveStackingFine() {
        int defensivePlayers = 0;
        for (Player player : players) {
            if (player.getPosition().equals("defender") || player.getPosition().equals("goalkeeper")) {
                ++defensivePlayers;
            }
        }
        if (defensivePlayers == 2) {
            expectedPoints -= 1;
        } else if (defensivePlayers == 3) {
            expectedPoints -= 3;
        }
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
