package com.example.fp_predictor.optimization.knapsack.dynamic;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.stacks.Stackable;

import java.util.*;

/**
 * Ключ для динамического массива, хранящий информацию о расстановке состава и командах,
 * игроки которых в составе представлены.
 */
public class MapKey {

    /** Количество вратарей в составе. */
    private int goalkeepers = 0;

    /** Количество защитников в составе. */
    private int defenders = 0;

    /** Количество полузащитников в составе. */
    private int midfielders = 0;

    /** Количество нападающих в составе. */
    private int forwards = 0;

    /** Множество команд, из игроков которых состоит состав. */
    private final Set<String> teams;

    public MapKey(Stackable stackable) {
        teams = new HashSet<>();
        extractPositions(stackable);
        teams.add(stackable.getTeam());
    }

    public MapKey(MapKey mapKey, Stackable stackable) {
        teams = new HashSet<>();
        extractPositions(mapKey);
        extractPositions(stackable);
        teams.add(stackable.getTeam());
        teams.addAll(mapKey.teams);
    }

    /**
     * Добавление новго стека в команду.
     * @param stackable - новый стек.
     */
    public void addStackable(Stackable stackable) {
        extractPositions(stackable);
        teams.add(stackable.getTeam());
    }

    /**
     * Проверка, имеюся ли игроки заданной команды в составе по текущему ключу.
     * @param team - проверяемая команда.
     * @return - TRUE, если имеются; FALSE в противном случае.
     */
    public boolean containsTeam(String team) {
        return teams.contains(team);
    }

    /**
     * Извлечь расстановку заданного ключа.
     * @param mapKey - проверяемый ключ.
     */
    private void extractPositions(MapKey mapKey) {
        goalkeepers += mapKey.goalkeepers;
        defenders += mapKey.defenders;
        midfielders += mapKey.midfielders;
        forwards += mapKey.forwards;
    }

    /**
     * Извлечь расстановку заданного стека.
     * @param stackable - проверяемый стек.
     */
    private void extractPositions(Stackable stackable) {
        for (Player player : stackable.getPlayers()) {
            switch (player.getPosition()) {
                case "goalkeeper":
                    ++goalkeepers;
                    break;
                case "defender":
                    ++defenders;
                    break;
                case "midfielder":
                    ++midfielders;
                    break;
                case "forward":
                    ++forwards;
                    break;
            }
        }
    }

    /**
     * Проверка, имеются ли у текущего ключа пересечения по командам с заданным.
     * @param mapKey - проверяемый ключ.
     * @return - TRUE, сли имеются; FALSE в противном случае.
     */
    public boolean hasSameTeams(MapKey mapKey) {
        return teams.containsAll(mapKey.teams) && mapKey.teams.containsAll(teams);
    }

    public int getGoalkeepers() {
        return goalkeepers;
    }

    public int getDefenders() {
        return defenders;
    }

    public int getMidfielders() {
        return midfielders;
    }

    public int getForwards() {
        return forwards;
    }

    public Set<String> getTeams() {
        return teams;
    }

    /**
     * Объекты считаются равными, если они хранят информацию о составе с одинаковой расстановкой
     * и одинаковым набором команд, игроки которых в текущем составе представлены.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapKey mapKey = (MapKey) obj;

        boolean sameLineUp = goalkeepers == mapKey.getGoalkeepers()
                && defenders == mapKey.getDefenders()
                && midfielders == mapKey.getMidfielders()
                && forwards == mapKey.getForwards();

        return this.hasSameTeams(mapKey) && sameLineUp;
    }

    @Override
    public int hashCode() {
        String[] teamsArray = {"", "", "", ""};
        for (String team : teams) {
            teamsArray[0] = team;
        }
        return Objects.hash(
                goalkeepers, defenders, midfielders, forwards,
                teamsArray[0], teamsArray[1], teamsArray[2], teamsArray[3]
        );
    }

    @Override
    public String toString() {
        return teams + " " + goalkeepers + " " + defenders + " " + midfielders + " " + forwards;
    }
}
