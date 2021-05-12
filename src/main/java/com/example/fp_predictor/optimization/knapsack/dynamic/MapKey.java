package com.example.fp_predictor.optimization.knapsack.dynamic;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.stacks.Stackable;

import java.util.*;

public class MapKey {

    private int goalkeepers = 0;
    private int defenders = 0;
    private int midfielders = 0;
    private int forwards = 0;
    /*private String[] teams = {"", "", "", ""};*/
    private Set<String> teams;

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

    public void addStackable(Stackable stackable) {
        extractPositions(stackable);
        teams.add(stackable.getTeam());
    }

    public boolean containsTeam(String team) {
        return teams.contains(team);
    }

    private void extractPositions(MapKey mapKey) {
        goalkeepers += mapKey.goalkeepers;
        defenders += mapKey.defenders;
        midfielders += mapKey.midfielders;
        forwards += mapKey.forwards;
    }

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

    @Override
    public int hashCode() {

        String[] teamsArray = {"", "", "", ""};
        int count = 0;
        for (String team : teams) {
            teamsArray[0] = team;
        }
        return Objects.hash(
                goalkeepers, defenders, midfielders, forwards,
                teamsArray[0], teamsArray[1], teamsArray[2], teamsArray[3]
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MapKey mapKey = (MapKey) obj;

        boolean sameTeams = this.hasSameTeams(mapKey);
        boolean sameLineUp = goalkeepers == mapKey.getGoalkeepers()
                && defenders == mapKey.getDefenders()
                && midfielders == mapKey.getMidfielders()
                && forwards == mapKey.getForwards();

        return sameTeams && sameLineUp;
    }

    @Override
    public String toString() {
        return teams + " " + goalkeepers + " " + defenders + " " + midfielders + " " + forwards;
    }
}
