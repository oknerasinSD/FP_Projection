package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;
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
    private List<PlayerForecast> forecasts;

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
    private Map<String, List<PlayerForecast>> forecastsByTeam = new HashMap<>();

    /** Мапа вида <Команда : Список дабл-стеков команды>. */
    private Map<String, List<DoubleStack>> doubleStacksByTeam = new HashMap<>();

    /** Мапа вида <Команда : Список трипл-стеков команды>. */
    private Map<String, List<TripleStack>> tripleStacksByTeam = new HashMap<>();

    /**
     * Конструктор.
     * @param forecasts - список объектов с ожиданием и ценами для отдельных игроков.
     * @param league - лига, для турнира по которой проводится расчет.
     * @param chosenTeams - выбранные пользователем команды.
     */
    public Stacks(List<PlayerForecast> forecasts, League league, Set<String> chosenTeams) {
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
    }

    /**
     * Построение мапы вида <Команда : Список игроков команды>.
     * @param league - лига, для команд которой выполняется построение.
     */
    private void buildForecastsByTeam(League league) {
        List<String> teams = new Teams().getTeams(league);
        for (String team : teams) {
            List<PlayerForecast> newList = new ArrayList<>();
            forecastsByTeam.put(team, newList);
        }
        for (PlayerForecast player : forecasts) {
            forecastsByTeam.get(player.getTeam()).add(player);
        }
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
            List<PlayerForecast> teamList = forecastsByTeam.get(team);
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
            List<PlayerForecast> teamList = forecastsByTeam.get(team);
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

    public List<PlayerForecast> getForecasts() {
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
}