package com.example.fp_predictor.optimization.knapsack;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.stacks.DoubleStack;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.TripleStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Фэнтези-команда.
 */
public class FantasyTeam {

    /** ID турнира в системе FanTeam. */
    private long fanTeamTournamentId;

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
    private int price_x_10 = 0;

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

    private Set<String> sizes;

    public FantasyTeam(long tournamentId) {
        this.fanTeamTournamentId = tournamentId;
    }

    public FantasyTeam(
            TripleStack tripleStack1,
            TripleStack tripleStack2,
            TripleStack tripleStack3,
            DoubleStack doubleStack,
            long tournamentId
    ) {
        this.fanTeamTournamentId = tournamentId;
        tripleStacks.addAll(Arrays.asList(tripleStack1, tripleStack2, tripleStack3));
        doubleStacks.add(doubleStack);
        findCaptains();
        countExpectedPoints();
    }

    public FantasyTeam(Stackable stackable, long fanTeamTournamentId) {
        this.fanTeamTournamentId = fanTeamTournamentId;
        addUnknownStack(stackable);
        findCaptains();
        countExpectedPoints();
        price_x_10 = stackable.getPrice_x_10();
    }

    public FantasyTeam(FantasyTeam team, Stackable stack, long tournamentId) {
        this.fanTeamTournamentId = tournamentId;
        tripleStacks.addAll(team.getTripleStacks());
        doubleStacks.addAll(team.getDoubleStacks());
        this.addUnknownStack(stack);
        findCaptains();
        countExpectedPoints();
        price_x_10 = team.price_x_10 + stack.getPrice_x_10();
    }

    public FantasyTeam(List<Stackable> stackables, int tournamentId) {
        this.fanTeamTournamentId = tournamentId;
        for (Stackable stackable : stackables) {
            addUnknownStack(stackable);
        }
        findCaptains();
        countExpectedPoints();
    }

    private void countExpectedPoints() {
        for (TripleStack tripleStack : tripleStacks) {
            for (Player player : tripleStack.getPlayers()) {
                expectedPoints += player.getExpectedPoints();
            }
        }
        for (DoubleStack doubleStack : doubleStacks) {
            for (Player player : doubleStack.getPlayers()) {
                expectedPoints += player.getExpectedPoints();
            }
        }
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
            sumTeamPositions();
            return checkPositions() && checkTeams();
        }
    }

    private boolean checkTeams() {
        Set<String> teams = new HashSet<>();
        for (TripleStack tripleStack : tripleStacks) {
            teams.add(tripleStack.getTeam());
        }
        for (DoubleStack doubleStack : doubleStacks) {
            teams.add(doubleStack.getTeam());
        }
        return teams.size() == 4;
    }

    public boolean isPreliminarilyValid() {
        if (tripleStacks.size() > 3 || doubleStacks.size() > 1) {
            return false;
        } else {
            sumTeamPositions();
            return price_x_10 <= 1000
                    && positions.get("goalkeeper") <= 1
                    && positions.get("defender") <= 3
                    && positions.get("midfielder") <= 5
                    && positions.get("forward") <= 3;
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
    private void sumTeamPositions() {
        for (TripleStack stack : tripleStacks) {
            sumStackPositions(stack);
        }
        for (DoubleStack stack : doubleStacks) {
            sumStackPositions(stack);
        }
    }

    /**
     * Вычисление количеситва игроков на каждой позиции в стеке.
     * @param stack - стек.
     */
    private void sumStackPositions(Stackable stack) {
        for (Player player : stack.getPlayers()) {
             positions.put(player.getPosition(), positions.get(player.getPosition()) + 1);
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
                && positions.get("defender") == 3 /*&& positions.get("defender") <= 5*/
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
        price_x_10 += stack.getPrice_x_10();
    }

    /**
     * Добавление дабл-стека в команду.
     * @param stack - дабл-стек.
     */
    public void addDoubleStack(DoubleStack stack) {
        doubleStacks.add(stack);
        expectedPoints += stack.getExpectedPoints();
        price_x_10 += stack.getPrice_x_10();
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
        writer.write(fanTeamTournamentId + ",");
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
        return expectedPoints;
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

    public int getPrice_x_10() {
        return price_x_10;
    }

    public long getCaptainId() {
        return captainId;
    }

    public long getViceCaptainId() {
        return viceCaptainId;
    }

    @Override
    public String toString() {
        return tripleStacks + " " + doubleStacks;
    }
}
