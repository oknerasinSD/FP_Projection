package com.example.fp_predictor.optimization.knapsack.dynamic;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.knapsack.FantasyTeam;
import com.example.fp_predictor.optimization.stacks.DoubleStack;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.Stacks;
import com.example.fp_predictor.optimization.stacks.TripleStack;
import com.example.fp_predictor.scraping.League;

import java.util.List;
import java.util.Set;

public class Dynamic {

    private MatrixElement[][] matrix;
    private Set<String> chosenTeams;
    private FantasyTeam finalTeam;
    private long fanTeamTournamentId;
    private List<Stackable> stacksList;
    private Stacks stacks;
    int startLimit = 0;

    public Dynamic(List<Player> players, League league, Set<String> chosenTeams, long fanTeamTournamentId) {
        this.chosenTeams = chosenTeams;
        this.fanTeamTournamentId = fanTeamTournamentId;
        stacks = new Stacks(players, league, chosenTeams);
        stacks.build();
        finalTeam = new FantasyTeam(fanTeamTournamentId);
    }

    public void solve() {
        stacksList = stacks.getAllStacks();
        matrix = new MatrixElement[stacksList.size() + 1][1001];
        defineStartLimit();
        count();
    }

    private void defineStartLimit() {
        switch (chosenTeams.size()) {
            case 0:
                startLimit = stacksList.size();
                break;
            case 1:
            case 2:
            case 3:
                String firstTeam = stacksList.get(0).getTeam();
                while (firstTeam.equals(stacksList.get(startLimit).getTeam())) {
                    ++startLimit;
                }
                /*startLimit = 1;*/
                break;
            case 4:
                startLimit = stacksList.size();
                break;
        }
    }

    private void count() {
        initMatrix();
        /*System.out.println(stacksList.size());
        System.out.println(startLimit);*/
        for (int i = 1; i <= stacksList.size(); i++) {
            /*System.out.println(i);*/
            for (int j = 1; j < matrix[i].length; j++) {
                matrix[i][j].putAll(matrix[i - 1][j]);
                int delta = j - stacksList.get(i - 1).getPrice_x_10();
                if (i <= startLimit && delta == 0) {
                    MapKey mapKey = new MapKey(stacksList.get(i - 1));
                    FantasyTeam fantasyTeam = new FantasyTeam(stacksList.get(i - 1), fanTeamTournamentId);
                    if (!matrix[i][j].containsKey(mapKey) || newTeamBetter(matrix[i][j], mapKey, fantasyTeam)) {
                        matrix[i][j].put(mapKey, fantasyTeam);
                    }
                } else {
                    for (MapKey mapKey : matrix[i - 1][j].getKeySet()) {
                        int newPrice = j + stacksList.get(i - 1).getPrice_x_10();
                        if (mapKey.containsTeam(stacksList.get(i - 1).getTeam())) {
                            continue;
                        }
                        if (stacksList.get(i - 1) instanceof DoubleStack
                                && matrix[i - 1][j].get(mapKey).getDoubleStacks().size() == 1) {
                            continue;
                        }
                        if (stacksList.get(i - 1) instanceof TripleStack
                                && matrix[i - 1][j].get(mapKey).getTripleStacks().size() == 3) {
                            continue;
                        }
                        if (newPrice <= 1000) {
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
                                    matrix[i][newPrice].remove(newMapKey);
                                    matrix[i][newPrice].put(newMapKey, fantasyTeam);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean newTeamBetter(MatrixElement element, MapKey key, FantasyTeam newTeam) {
        return element.containsKey(key) && newTeam.getExpectedPoints() > element.getKeyTeamExpectedPoints(key);
    }

    private void initMatrix() {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new MatrixElement();
            }
        }
    }

    public FantasyTeam getFinalTeam() {
        return finalTeam;
    }
}