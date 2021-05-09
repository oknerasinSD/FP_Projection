package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.domain.Player;

/**
 * Интерфейс, который должны реализовывать классы, описывающие стеки
 */
public interface Stackable {

    public double getPrice();
    public double getExpectedPoints();
    public double getValue();
    public Player[] getPlayers();
    public int getNumberOfPlayers();
    public String getTeam();
    public Boolean isInSameTeam(Stackable stackable);
}
