package com.example.fp_predictor.optimization.knapsack;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;
import com.example.fp_predictor.optimization.stacks.DoubleStack;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.TripleStack;

import java.util.*;

/**
 * Фэнтези-команда.
 */
public class FantasyTeam /*implements Stackable*/ {

    /** Список трипл-стеков команды. */
    private List<TripleStack> tripleStacks = new ArrayList<>();

    /** Список дабл-стеков команды. */
    private List<DoubleStack> doubleStacks = new ArrayList<>();

    /** Мапа вида <Позиция : Количество игроков на этой позиции>. */
    private Map<String, Integer> positions = new HashMap<>(Map.of(
            "goalkeeper", 0,
            "defender", 0,
            "midfielder", 0,
            "forward", 0
    ));

    /** Цена команды. */
    private double price = 0;

    private double expectedPoints = 0;

    public FantasyTeam() {}

    public FantasyTeam(
            TripleStack tripleStack1,
            TripleStack tripleStack2,
            TripleStack tripleStack3,
            DoubleStack doubleStack
    ) {
        tripleStacks.addAll(Arrays.asList(tripleStack1, tripleStack2, tripleStack3));
        doubleStacks.add(doubleStack);
    }

    public FantasyTeam(FantasyTeam team, Stackable stack) {
        tripleStacks.addAll(team.getTripleStacks());
        doubleStacks.addAll(team.getDoubleStacks());
        this.addUnknownStack(stack);
    }

    public FantasyTeam(List<Stackable> stackables) {
        for (Stackable stackable : stackables) {
            addUnknownStack(stackable);
        }
    }

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

    public boolean willBeValid(Stackable stackable) {
        int goalkeepers = positions.get("goalkeeper");
        int defenders = positions.get("defender");
        int midfielders = positions.get("midfielder");
        int forwards = positions.get("forward");
        for (PlayerForecast player : stackable.getPlayers()) {
            if ("goalkeeper".equals(player.getPosition())) {
                ++goalkeepers;
            } else if ("defender".equals(player.getPosition())) {
                ++defenders;
            } else if ("midfielder".equals(player.getPosition())) {
                ++midfielders;
            } else if ("forward".equals(player.getPosition())) {
                ++forwards;
            };
        }
        return goalkeepers <= 1 && defenders <= 5 && midfielders <= 5 && forwards <= 3 && doubleStacks.size() <= 2;
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
                && positions.get("defender") >= 3 && positions.get("defender") <= 5
                && positions.get("midfielder") >= 2 && positions.get("midfielder") <= 5
                && positions.get("forward") >= 1 && positions.get("forward") <= 3;
    }

    /**
     * Добавление трипл-стека в команду.
     * @param stack - трипл-стек.
     */
    public void addTripleStack(TripleStack stack) {
        tripleStacks.add(stack);
        expectedPoints += stack.getExpectedPoints();
        price += stack.getPrice();
    }

    /**
     * Добавление дабл-стека в команду.
     * @param stack - дабл-стек.
     */
    public void addDoubleStack(DoubleStack stack) {
        doubleStacks.add(stack);
        expectedPoints += stack.getExpectedPoints();
        price += stack.getPrice();
    }

    public void addUnknownStack(Stackable stackable) {
        if (stackable instanceof DoubleStack) {
            addDoubleStack((DoubleStack) stackable);
        } else if (stackable instanceof TripleStack) {
            addTripleStack((TripleStack) stackable);
        }
    }

    public double getExpectedPoints() {
        double points = 0;
        for (TripleStack stack : tripleStacks) {
            points += stack.getExpectedPoints();
        }
        for (DoubleStack stack : doubleStacks) {
            points += stack.getExpectedPoints();
        }
        return points;
    }

    public List<TripleStack> getTripleStacks() {
        return tripleStacks;
    }

    public List<DoubleStack> getDoubleStacks() {
        return doubleStacks;
    }

    /*@Override
    public double getPrice() {
        return price;
    }

    @Override
    public PlayerForecast[] getPlayers() {
        return new PlayerForecast[0];
    }

    @Override*/
    public int getNumberOfPlayers() {
        return 3 * tripleStacks.size() + 2 * doubleStacks.size();
    }

    /*@Override
    public String toString() {
        OutputPlayer goalkeeper = null;
        List<OutputPlayer> defenders = new ArrayList<>();
        List<OutputPlayer> midfielders= new ArrayList<>();
        List<OutputPlayer> forwards = new ArrayList<>();
        for (TripleStack stack : tripleStacks) {
            for (PlayerForecast player: stack.getPlayers()) {
                if ("goalkeeper".equals(player.getPosition())) {
                    goalkeeper = new OutputPlayer(player.getName(), player.getPrice(), player.getTeam());
                } else if ("defender".equals(player.getPosition())) {
                    defenders.add(new OutputPlayer(player.getName(), player.getPrice(), player.getTeam()));
                } else if ("midfielder".equals(player.getPosition())) {
                    midfielders.add(new OutputPlayer(player.getName(), player.getPrice(), player.getTeam()));
                } else if ("forward".equals(player.getPosition())) {
                    forwards.add(new OutputPlayer(player.getName(), player.getPrice(), player.getTeam()));
                }
            }
        }
        for (DoubleStack stack : doubleStacks) {
            for (PlayerForecast player: stack.getPlayers()) {
                if ("goalkeeper".equals(player.getPosition())) {
                    goalkeeper = new OutputPlayer(player.getName(), player.getPrice(), player.getTeam());
                } else if ("defender".equals(player.getPosition())) {
                    defenders.add(new OutputPlayer(player.getName(), player.getPrice(), player.getTeam()));
                } else if ("midfielder".equals(player.getPosition())) {
                    midfielders.add(new OutputPlayer(player.getName(), player.getPrice(), player.getTeam()));
                } else if ("forward".equals(player.getPosition())) {
                    forwards.add(new OutputPlayer(player.getName(), player.getPrice(), player.getTeam()));
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder(goalkeeper.toString());
        stringBuilder.append("\n");
        for (OutputPlayer player : defenders) {
            stringBuilder.append(player.toString()).append("\n");
        }
        for (OutputPlayer player : midfielders) {
            stringBuilder.append(player.toString()).append("\n");
        }
        for (OutputPlayer player : forwards) {
            stringBuilder.append(player.toString()).append("\n");
        }
        return stringBuilder.toString();
    }*/

    public boolean containsStake(Stackable stack) {
        return doubleStacks.contains(stack) || tripleStacks.contains(stack);
    }

    @Override
    public String toString() {
        return tripleStacks + " " + doubleStacks;
    }
}
