package com.example.fp_predictor.optimization.knapsack.dynamic;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.knapsack.FantasyTeam;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.Stacks;
import com.example.fp_predictor.scraping.League;

import java.util.List;
import java.util.Set;

/**
 * Динамический алгоритм решения задачи оптимизации.
 */
public class Dynamic {

    /** Динамическая матрица. */
    private MatrixElement[][] matrix;

    /** Выбранные пользователем команды. */
    private Set<String> chosenTeams;

    /** Итоговая команда. */
    private FantasyTeam finalTeam;

    /** ID утурнира в системе FanTeam. */
    private long fanTeamTournamentId;

    /** Список стеков, по которым строится команда. */
    private List<Stackable> stacksList;

    /** Объект типа Stacks - обертка над стеками. */
    private Stacks stacks;

    private int budget = 1050;

    /** Ограничение на количество стеков, с которых начинается построение новых команд
     * для случая выбора 1, 2 или 3 команд пользователем. */
    int startLimit = 0;

    public Dynamic(List<Player> players, League league, Set<String> chosenTeams, long fanTeamTournamentId) {
        this.chosenTeams = chosenTeams;
        this.fanTeamTournamentId = fanTeamTournamentId;
        stacks = new Stacks(players, league, chosenTeams);
        stacks.build();
        stacksList = stacks.getAllStacks();
        defineStartLimit();
        matrix = new MatrixElement[stacksList.size() + 1][1051];
        finalTeam = new FantasyTeam(fanTeamTournamentId);
    }


    /**
     * Алгоритм решения задачи оптимизации.
     */
    public void solve() {
        initMatrix();
        System.out.println(stacksList.size());
        for (int i = 1; i <= stacksList.size(); i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                matrix[i][j].combine(matrix[i - 1][j]);
                int delta = j - stacksList.get(i - 1).getPrice_x_10();
                if (delta == 0 && i <= startLimit) {
                    MapKey mapKey = new MapKey(stacksList.get(i - 1));
                    FantasyTeam fantasyTeam = new FantasyTeam(stacksList.get(i - 1), fanTeamTournamentId);
                    if (!matrix[i][j].containsKey(mapKey) || newTeamBetter(matrix[i][j], mapKey, fantasyTeam)) {
                        matrix[i][j].put(mapKey, fantasyTeam);
                    }
                } else {
                    for (MapKey mapKey : matrix[i - 1][j].getKeySet()) {
                        int newPrice = j + stacksList.get(i - 1).getPrice_x_10();
                        if (newPrice <= budget) {
                            FantasyTeam fantasyTeam = new FantasyTeam(
                                    matrix[i - 1][j].get(mapKey),
                                    stacksList.get(i - 1),
                                    fanTeamTournamentId
                            );
                            if (fantasyTeam.isValid()
                                    && fantasyTeam.getExpectedPoints() > finalTeam.getExpectedPoints()) {
                                finalTeam = fantasyTeam;
                                continue;
                            }
                            if (fantasyTeam.isPreliminarilyValid()) {
                                MapKey newMapKey = new MapKey(mapKey, stacksList.get(i - 1));
                                if (!matrix[i][newPrice].containsKey(newMapKey)
                                        || newTeamBetter(matrix[i][newPrice], newMapKey, fantasyTeam)) {
                                    matrix[i][newPrice].put(newMapKey, fantasyTeam);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Проверка, является ли новая команда по заданному ключу более выгодной, чем уже имеющаяся.
     * @param element - элемент динамического массива;
     * @param key - ключ;
     * @param newTeam - новая команда.
     * @return - TRUE, если команда является более выгодной; FALSE в противном случае.
     */
    private boolean newTeamBetter(MatrixElement element, MapKey key, FantasyTeam newTeam) {
        return element.containsKey(key) && newTeam.getExpectedPoints() > element.getKeyTeamExpectedPoints(key);
    }

    /**
     * Инициализация динамической матрицы.
     */
    private void initMatrix() {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new MatrixElement();
            }
        }
    }

    /**
     * Определение ограничения на количество стеков, с которых начинается построение новых составов.
     * Зависит от количества выбранных пользователем команд.
     */
    private void defineStartLimit() {
        switch (chosenTeams.size()) {
            case 0:
                startLimit = (int) (stacksList.size());
                break;
            case 1:
            case 2:
            case 3:
                String firstTeam = stacksList.get(0).getTeam();
                while (firstTeam.equals(stacksList.get(startLimit).getTeam())) {
                    ++startLimit;
                }
                break;
            case 4:
                startLimit = stacksList.size();
                break;
        }
    }

    public FantasyTeam getFinalTeam() {
        return finalTeam;
    }
}