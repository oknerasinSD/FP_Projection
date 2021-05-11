package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.stacks.data.Teams;
import com.example.fp_predictor.scraping.League;

import java.util.*;

/**
 * Класс для построения стеков.
 */
public class Stacks {

    /** Лига, для команд которой строятся стеки. */
    private final League league;

    /** Список игроков, из которого собираются стеки. */
    private List<Player> forecasts;

    /** Список команд, игроков которых пользователь хочет видеть в фэнтези-составе. */
    private final Set<String> chosenTeams;

    /** Мапа вида <Команда : Список дабл-стеков команды> для команд, выьранных пользователем. */
    private List<DoubleStack> chosenDoubleStacks = new ArrayList<>();

    /** Мапа вида <Команда : Список трипл-стеков команды> для команд, выбранных пользователем. */
    private List<TripleStack> chosenTripleStacks = new ArrayList<>();

    /** Список трипл-стеков для команд, не выбранных пользователем. */
    private List<TripleStack> restTripleStacks = new ArrayList<>();

    /** Список дабл-стеков для команд, не выбранных пользователем. */
    private List<DoubleStack> restDoubleStacks = new ArrayList<>();

    /** Мапа вида <Команда : Список игроков команды>. */
    private Map<String, List<Player>> forecastsByTeam = new HashMap<>();

    /** Мапа вида <Команда : Список дабл-стеков команды>. */
    private Map<String, List<DoubleStack>> doubleStacksByTeam = new HashMap<>();

    /** Мапа вида <Команда : Список трипл-стеков команды>. */
    private Map<String, List<TripleStack>> tripleStacksByTeam = new HashMap<>();

    private List<Stackable> stacksForChoice0123 = new ArrayList<>();

    private List<Stackable> stacksForChoice4 = new ArrayList<>();

    /**
     * Конструктор.
     * @param forecasts - список объектов с ожиданием и ценами для отдельных игроков.
     * @param league - лига, для турнира по которой проводится расчет.
     * @param chosenTeams - выбранные пользователем команды.
     */
    public Stacks(List<Player> forecasts, League league, Set<String> chosenTeams) {
        this.forecasts = forecasts;
        this.league = league;
        this.chosenTeams = chosenTeams;
    }

    /**
     * Построение стеков.
     */
    public void build() {
        buildForecastsByTeam(league);
        buildDoubleStacksByTeam();
        buildTripleStacksByTeam();
        if (chosenTeams.size() != 0) {
            buildChosenTripleStacks();
            buildChosenDoubleStacks();
        }
        if (chosenTeams.size() != 4) {
            buildRestStacks();
        }
        stacksForChoice0123.addAll(chosenTripleStacks);
        stacksForChoice0123.addAll(chosenDoubleStacks);
        stacksForChoice0123.addAll(restTripleStacks);
        stacksForChoice0123.addAll(restDoubleStacks);
        stacksForChoice4.addAll(chosenTripleStacks);
        stacksForChoice4.addAll(chosenDoubleStacks);
    }

    /**
     * Построение мапы вида <Команда : Список игроков команды>.
     * @param league - лига, для команд которой выполняется построение.
     */
    private void buildForecastsByTeam(League league) {
        List<String> teams = new Teams().getTeams(league);
        for (String team : teams) {
            List<Player> newList = new ArrayList<>();
            forecastsByTeam.put(team, newList);
        }
        for (Player player : forecasts) {
            forecastsByTeam.get(player.getTeam()).add(player);
        }
        filterForecastByTeam();
    }

    private void filterForecastByTeam() {
        for (String team : forecastsByTeam.keySet()) {

            Player goalkeeper = null;
            List<Player> defendersList = new ArrayList<>();
            List<Player> midfieldersList = new ArrayList<>();
            List<Player> forwardsList = new ArrayList<>();

            goalkeeper = buildPositionsLists(team, goalkeeper, defendersList, midfieldersList, forwardsList);
            sortPositionLists(defendersList, midfieldersList, forwardsList);

            forecastsByTeam.get(team).clear();
            if (goalkeeper != null) {
                forecastsByTeam.get(team).add(goalkeeper);
            }
            int defendersCount = addDefendersToForecasts(team, defendersList);
            int midfieldersCount = addMidfieldersToForecasts(team, midfieldersList);
            int forwardsCount = addForwardsToForecasts(team, forwardsList);
        }

    }

    private int addForwardsToForecasts(String team, List<Player> forwardsList) {
        int forwardsCount = 0;
        if (forwardsList.size() >= 2) {
            forecastsByTeam.get(team).add(forwardsList.get(0));
            forecastsByTeam.get(team).add(forwardsList.get(1));
            forwardsCount = 2;
        } else {
            for (Player player : forwardsList) {
                forecastsByTeam.get(team).add(player);
                ++forwardsCount;
            }
        }
        return forwardsCount;
    }

    private int addMidfieldersToForecasts(String team, List<Player> midfieldersList) {
        int midfieldersCount = 0;
        if (midfieldersList.size() >= 2) {
            forecastsByTeam.get(team).add(midfieldersList.get(0));
            forecastsByTeam.get(team).add(midfieldersList.get(1));
            midfieldersCount = 2;
        } else {
            for (Player player : midfieldersList) {
                forecastsByTeam.get(team).add(player);
                ++midfieldersCount;
            }
        }
        return midfieldersCount;
    }

    private int addDefendersToForecasts(String team, List<Player> defendersList) {
        int defendersCount = 0;
        if (defendersList.size() >= 2) {
            forecastsByTeam.get(team).add(defendersList.get(0));
            forecastsByTeam.get(team).add(defendersList.get(1));
            defendersCount = 2;
        } else {
            for (Player player : defendersList) {
                ++defendersCount;
                forecastsByTeam.get(team).add(player);
            }
        }
        if (team == "LIV") {
            System.out.println(defendersList);
        }
        return defendersCount;
    }

    private void sortPositionLists(List<Player> defendersList, List<Player> midfieldersList, List<Player> forwardsList) {
        defendersList.sort(Comparator.comparingDouble((Player o) -> -o.getExpectedPoints()));
        midfieldersList.sort(Comparator.comparingDouble((Player o) -> -o.getExpectedPoints()));
        forwardsList.sort(Comparator.comparingDouble((Player o) -> -o.getExpectedPoints()));
    }

    private Player buildPositionsLists(String team, Player goalkeeper, List<Player> defendersList, List<Player> midfieldersList, List<Player> forwardsList) {
        for (Player player : forecastsByTeam.get(team)) {
            switch (player.getPosition()) {
                case "goalkeeper":
                    goalkeeper = player;
                    continue;
                case "defender":
                    defendersList.add(player);
                    break;
                case "midfielder":
                    midfieldersList.add(player);
                    break;
                case "forward":
                    forwardsList.add(player);
                    break;
            }
        }
        return goalkeeper;
    }

    /**
     * Построение мапы вида <Команда : Список трипл-стеков> для выбранных игроком команд.
     */
    private void buildChosenTripleStacks() {
        for (String team : chosenTeams) {
            chosenTripleStacks.addAll(tripleStacksByTeam.get(team));
        }
    }

    /**
     * Построение мапы вида <Команда : Список дабл-стеков> для выбранных игроком команд.
     */
    private void buildChosenDoubleStacks() {
        for (String team : chosenTeams) {
            chosenDoubleStacks.addAll(doubleStacksByTeam.get(team));
        }
    }

    /**
     * Построение мап вида <Команда : Список дабл-стеков> и <Команда : Список трипл-стеков>
     * для не выбранных игроком команд.
     */
    private void buildRestStacks() {
        for (String team : forecastsByTeam.keySet()) {
            if (!chosenTeams.contains(team)) {
                restDoubleStacks.addAll(doubleStacksByTeam.get(team));
                restTripleStacks.addAll(tripleStacksByTeam.get(team));
            }
        }
    }

    /**
     * Построение дабл-стеков для команд.
     */
    private void buildDoubleStacksByTeam() {
        for (String team : forecastsByTeam.keySet()) {
            List<Player> teamList = forecastsByTeam.get(team);
            List<DoubleStack> teamDoubleStacks = new ArrayList<>();
            for (int i = 0; i < teamList.size() - 1; ++i) {
                for (int j = i + 1; j < teamList.size(); ++j) {
                    DoubleStack doubleStack = new DoubleStack(teamList.get(i), teamList.get(j));
                    teamDoubleStacks.add(doubleStack);
                }
            }
            doubleStacksByTeam.put(team, teamDoubleStacks);
        }
    }

    /**
     * Построение трипл-стеков для команд.
     */
    private void buildTripleStacksByTeam() {
        for (String team : forecastsByTeam.keySet()) {
            List<Player> teamList = forecastsByTeam.get(team);
            List<TripleStack> teamTripleStacks = new ArrayList<>();
            for (int i = 0; i < teamList.size() - 2; ++i) {
                for(int j = i + 1; j < teamList.size() - 1; ++j) {
                    for (int k = j + 1; k < teamList.size(); ++k) {
                        TripleStack tripleStack = new TripleStack(teamList.get(i), teamList.get(j), teamList.get(k));
                        teamTripleStacks.add(tripleStack);
                    }
                }
            }
            tripleStacksByTeam.put(team, teamTripleStacks);
        }
    }

    public List<DoubleStack> getTeamDoubleStacks(String team) {
        return doubleStacksByTeam.get(team);
    }

    public List<TripleStack> getTeamTripleStacks(String team) {
        return tripleStacksByTeam.get(team);
    }

    public League getLeague() {
        return league;
    }

    public List<Player> getForecasts() {
        return forecasts;
    }

    public List<DoubleStack> getChosenDoubleStacks() {
        return chosenDoubleStacks;
    }

    public List<TripleStack> getChosenTripleStacks() {
        return chosenTripleStacks;
    }

    public List<TripleStack> getRestTripleStacks() {
        return restTripleStacks;
    }

    public List<DoubleStack> getRestDoubleStacks() {
        return restDoubleStacks;
    }

    public Map<String, List<DoubleStack>> getDoubleStacksByTeam() {
        return doubleStacksByTeam;
    }

    public Map<String, List<TripleStack>> getTripleStacksByTeam() {
        return tripleStacksByTeam;
    }

    public List<Stackable> getStacksForChoice0123() {
        return stacksForChoice0123;
    }

    public List<Stackable> getStacksForChoice4() {
        return stacksForChoice4;
    }

    public Map<String, List<Player>> getForecastsByTeam() {
        return forecastsByTeam;
    }
}