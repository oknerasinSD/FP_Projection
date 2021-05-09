package com.example.fp_predictor.optimization.knapsack;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;
import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.stacks.DoubleStack;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.TripleStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Фэнтези-команда.
 */
public class FantasyTeam {

    /** ID турнира в системе FanTeam. */
    private long tournamentId;

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

    /** Ожидаемые очки. */
    private double expectedPoints = 0;

    /** Имя капитана. */
    private String captainName;

    /** Имя вице-капитана. */
    private String viceCaptainName;

    /** Ожидаемые очки капитана. */
    private double captainExpectedPoints = 0;

    /** Ожидаемые очки вице-капитана. */
    private double viceCaptainExpectedPoints = 0;

    /** ID капитана в системе FanTeam. */
    private long captainId;

    /** ID вице-капитана в системе FanTeam. */
    private long viceCaptainId;

    public FantasyTeam(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public FantasyTeam(
            TripleStack tripleStack1,
            TripleStack tripleStack2,
            TripleStack tripleStack3,
            DoubleStack doubleStack,
            long tournamentId
    ) {
        this.tournamentId = tournamentId;
        tripleStacks.addAll(Arrays.asList(tripleStack1, tripleStack2, tripleStack3));
        doubleStacks.add(doubleStack);
        findCaptains();
    }

    public FantasyTeam(FantasyTeam team, Stackable stack, int tournamentId) {
        this.tournamentId = tournamentId;
        tripleStacks.addAll(team.getTripleStacks());
        doubleStacks.addAll(team.getDoubleStacks());
        this.addUnknownStack(stack);
        findCaptains();
    }

    public FantasyTeam(List<Stackable> stackables, int tournamentId) {
        this.tournamentId = tournamentId;
        for (Stackable stackable : stackables) {
            addUnknownStack(stackable);
        }
        findCaptains();
    }

    /**
     * Поиск капитана и вице-капитана.
     * Капитаном считается игрок с наивысшим матожиданием, вице-капитаном - игрок со вторым наивысшим матожиданием.
     */
    private void findCaptains() {
        for (TripleStack stack : tripleStacks) {
            checkStackForCaptains(stack);
        }
        for (DoubleStack stack : doubleStacks) {
            checkStackForCaptains(stack);
        }
    }

    /**
     * Проверка стека на наличие капитана или вице-капитана.
     * @param stackable - проверяемый стек.
     */
    private void checkStackForCaptains(Stackable stackable) {
        for (Player player : stackable.getPlayers()) {
            if (player.getExpectedPoints() > captainExpectedPoints) {
                viceCaptainExpectedPoints = captainExpectedPoints;
                viceCaptainName = captainName;
                viceCaptainId = captainId;
                captainExpectedPoints = player.getExpectedPoints();
                captainName = player.getName();
                captainId = player.getFanteamPlayerId();
            } else if (player.getExpectedPoints() > viceCaptainExpectedPoints) {
                viceCaptainExpectedPoints = player.getExpectedPoints();
                viceCaptainName = player.getName();
                viceCaptainId = player.getFanteamPlayerId();
            }
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
        for (Player player : stackable.getPlayers()) {
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
        for (Player player : stack.getPlayers()) {
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

    public void createFanTeamInputFile() throws IOException {
        File file = new File("FanTeamInput.csv");
        FileWriter writer = new FileWriter(file);
        writer.write(tournamentId + ",");
        for (TripleStack stack : tripleStacks) {
            for (Player player : stack.getPlayers()) {
                writer.write(player.getFanteamPlayerId() + ",");
            }
        }
        for (Player player : doubleStacks.get(0).getPlayers()) {
            writer.write(player.getFanteamPlayerId() + ",");
        }
        writer.write(captainId + ",");
        writer.write(String.valueOf(viceCaptainId));
        writer.close();
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

    public int getNumberOfPlayers() {
        return 3 * tripleStacks.size() + 2 * doubleStacks.size();
    }

    public boolean containsStake(Stackable stack) {
        return doubleStacks.contains(stack) || tripleStacks.contains(stack);
    }

    @Override
    public String toString() {
        return tripleStacks + " " + doubleStacks;
    }
}
