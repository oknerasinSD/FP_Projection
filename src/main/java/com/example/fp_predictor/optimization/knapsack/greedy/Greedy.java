package com.example.fp_predictor.optimization.knapsack.greedy;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.knapsack.FantasyTeam;
import com.example.fp_predictor.optimization.stacks.DoubleStack;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.Stacks;
import com.example.fp_predictor.optimization.stacks.TripleStack;
import com.example.fp_predictor.scraping.League;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Реализация жадного алгоритма для решения задачи оптимизации.
 */
public class Greedy {

    /** ID турнира в системе FanTeam. */
    private long tournamentId;

    /** Стеки для построения фэнтези-команды. */
    private Stacks stacks;

    /** Лучшая команда по ожидаемым очкам. */
    private FantasyTeam finalTeam;

    /** Выбранные пользователем команды. */
    private Set<String> chosenTeams;

    /** Список трипл-стеков из выбранных пользователем команд. */
    private List<TripleStack> chosenTripleStacks;

    /** Список дабл-стеков из выбранных пользователем команд. */
    private List<DoubleStack> chosenDoubleStacks;

    /** Список трипл-стеков из невыбранных пользователем команд. */
    private List<TripleStack> restTripleStacks;

    /** Список трипл-стеков из невыбранных пользователем команд. */
    private List<DoubleStack> restDoubleStacks;

    /**
     * Конструктор.
     * @param forecasts - список объектов с ожиданием и ценами для отдельных игроков.
     * @param league - лига, для турнира по которой проводится расчет.
     * @param chosenTeams - выбранные пользователем команды.
     */
    public Greedy(List<Player> forecasts, League league, Set<String> chosenTeams, long tournamentId) {

        this.chosenTeams = chosenTeams;
        this.tournamentId = tournamentId;
        stacks = new Stacks(forecasts, league, chosenTeams);
        stacks.build();

        chosenTripleStacks = stacks.getChosenTripleStacks();
        chosenDoubleStacks = stacks.getChosenDoubleStacks();
        restTripleStacks = stacks.getRestTripleStacks();
        restDoubleStacks = stacks.getRestDoubleStacks();
    }

    /**
     * Метод для запуска алгоритма.
     */
    public void solve() {

        switch (chosenTeams.size()) {
            case 0:
                solveFor0chosenTeams();
                break;
            case 1:
                solveFor1chosenTeam();
                break;
            case 2:
                solveFor2ChosenTeams();
                break;
            case 3:
                solveFor3chosenTeams();
                break;
            case 4:
                sortChosenStacks();
                solveFor4chosenTeams();
                break;
        }
    }

    /**
     * Решение для случая, когда пользователь выбрал 4 команды.
     */
    private void solveFor4chosenTeams() {
    }

    /**
     * Реализация для случая, когда пользователь выбрал 1 команду.
     */
    private void solveFor1chosenTeam() {
    }

    /**
     * Реализация для случая, когда пользователь выбрал 2 команды.
     */
    private void solveFor2ChosenTeams() {
    }

    /**
     * Реализация для случая, когда пользователь выбрал 3 команды.
     */
    private void solveFor3chosenTeams() {
    }

    /**
     * Реализация для случая, когда пользователь выбрал 0 команд.
     */
    private void solveFor0chosenTeams() {
        sortRestStacks();
        int size = restTripleStacks.size();
        FantasyTeam[][][] fantasyTeams = new FantasyTeam[size - 2][size - 1][size];
        for (int i = 0; i < size - 2; ++i) {
            String team1 = restTripleStacks.get(i).getTeam();
            fantasyTeams[i][0][0] = new FantasyTeam(restTripleStacks.get(i), tournamentId);
            for (int j = i + 1; j < size - 1; ++j) {
                String team2 = restTripleStacks.get(j).getTeam();
                if (team1.equals(team2)) {
                    continue;
                }
                fantasyTeams[i][j][0] = new FantasyTeam(fantasyTeams[i][0][0], restTripleStacks.get(j), tournamentId);
                if (!fantasyTeams[i][j][0].isPreliminarilyValid()) {
                    continue;
                }
                for (int k = j + 1; k < size; ++k) {
                    String team3 = restTripleStacks.get(k).getTeam();
                    if (team1.equals(team3) || team2.equals(team3)) {
                        continue;
                    }
                    fantasyTeams[i][j][k] =
                            new FantasyTeam(fantasyTeams[i][j][0], restTripleStacks.get(k), tournamentId);
                    if (fantasyTeams[i][j][k].isPreliminarilyValid()) {
                        continue;
                    }
                    for (DoubleStack doubleStack : restDoubleStacks) {
                        String team4 = doubleStack.getTeam();
                        if (team1.equals(team4) || team2.equals(team4) || team3.equals(team4)) {
                            continue;
                        }
                        FantasyTeam currentTeam = new FantasyTeam(
                                restTripleStacks.get(i),
                                restTripleStacks.get(j),
                                restTripleStacks.get(k),
                                doubleStack,
                                tournamentId
                        );
                        if (currentTeam.isValid()) {
                            finalTeam = currentTeam;
                            break;
                        }
                    }
                    if (finalTeam != null) {
                        break;
                    }
                }
                if (finalTeam != null) {
                    break;
                }
            }
            if (finalTeam != null) {
                break;
            }
        }
    }

    /**
     * Сортировка списков со стеками из игроков выбранных команд.
     */
    private void sortChosenStacks() {
        chosenDoubleStacks.sort(Comparator.comparingDouble(Stackable::getValue).reversed());
        chosenTripleStacks.sort(Comparator.comparingDouble(Stackable::getValue).reversed());
    }

    /**
     * Сортировка списков со стеками из игроков невыбранных команд.
     */
    private void sortRestStacks() {
        restDoubleStacks.sort(Comparator.comparingDouble(Stackable::getValue).reversed());
        restTripleStacks.sort(Comparator.comparingDouble(Stackable::getValue).reversed());
    }

    /**
     * Сортировка всех списков со стеками.
     */
    private void sortAllStacks() {
        sortChosenStacks();
        sortRestStacks();
    }

    /**
     * Проверка, есть ли в списке стеки из одной команды.
     * @param stackables - стеки.
     * @return - TRUE, если есть; FALSE, если нет.
     */
    private boolean isInSameTeam(List<Stackable> stackables) {
        boolean flag = false;
        for (int i = 0; i < stackables.size() - 1; i++) {
            for (int j = i + 1; j < stackables.size(); j++) {
                if (stackables.get(i).isInSameTeam(stackables.get(j))) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public FantasyTeam getFinalTeam() {
        return finalTeam;
    }
}
