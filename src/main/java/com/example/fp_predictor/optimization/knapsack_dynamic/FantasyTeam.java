package com.example.fp_predictor.optimization.knapsack_dynamic;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.TripleStack;
import com.example.fp_predictor.optimization.stacks.DoubleStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Фэнтези-команда.
 */
public class FantasyTeam {

    /** Список трипл-стеков команды. */
    private List<TripleStack> tripleStacks = new ArrayList<>();

    /** Список дабл-стеков команды. */
    private List<DoubleStack> doubleStacks = new ArrayList<>();

    /** Мапа вида <Позиция : Количество игроков на этой позиции>. */
    private Map<String, Integer> positions = Map.ofEntries(
            Map.entry("goalkeeper", 0),
            Map.entry("defender", 0),
            Map.entry("midfielder", 0),
            Map.entry("forward", 0)
    );

    /** Цена команды. */
    private double price = 0;

    /**
     * Проверка того, является ли команда валидной.
     * Условия валидности:
     *      - 3 трипл-стека;
     *      - 1 дабл-стек;
     *      - 1 вратарь;
     *      - число защитников = от 3 до 5;
     *      - число полузащитников = от 2 до 5;
     *      - число нападающих = от 1 до 3.
     * @return - TRUE, если команда валидна; FALSE, если команда невалидна.
     */
    public boolean isValid() {
        if (tripleStacks.size() != 3 || doubleStacks.size() != 1) {
            return false;
        } else {
            sumTeamPositionsAndPrice();
            return checkPositions() && price <= 100;
        }
    }

    /**
     * Вычисление количества игроков на каждой позиции в команде.
     */
    private void sumTeamPositionsAndPrice() {
        for (TripleStack stack : tripleStacks) {
            sumStackPositionsAndPrice(stack);
        }
        for (DoubleStack stack : doubleStacks) {
            sumStackPositionsAndPrice(stack);
        }
    }

    /**
     * Вычисление количеситва игроков на каждой позиции в стеке.
     * @param stack - стек.
     */
    private void sumStackPositionsAndPrice(Stackable stack) {
        for (PlayerForecast player : stack.getPlayers()) {
             positions.put(player.getPosition(), positions.get(player.getPosition()) + 1);
             price += player.getPrice();
        }
    }

    /**
     * Проверка, удовлетворяет ли количество игроков на позициях следующим условиям:
     *      - 1 вратарь;
     *      - число защитников = от 3 до 5;
     *      - число полузащитников = от 2 до 5;
     *      - число нападающих = от 1 до 3.
     */
    private boolean checkPositions() {
        return positions.get("goalkeeper") == 1
                && positions.get("defender") >= 3 && positions.get("Defenders") <= 5
                && positions.get("midfielder") >= 2 && positions.get("midfielder") <= 5
                && positions.get("forwards") >= 1 && positions.get("forward") <= 3;
    }

    /**
     * Добавление трипл-стека в команду.
     * @param stack - трипл-стек.
     */
    public void addTripleStack(TripleStack stack) {
        tripleStacks.add(stack);
    }

    /**
     * Добавление дабл-стека в команду.
     * @param stack - дабл-стек.
     */
    public void addDoubleStack(DoubleStack stack) {
        doubleStacks.add(stack);
    }

    public List<TripleStack> getTripleStacks() {
        return tripleStacks;
    }

    public List<DoubleStack> getDoubleStacks() {
        return doubleStacks;
    }
}
