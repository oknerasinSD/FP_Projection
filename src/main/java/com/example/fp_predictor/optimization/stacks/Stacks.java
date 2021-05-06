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
        buildChosenDoubleStacks();
        buildChosenTripleStacks();
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
     * Построение дабл-стеков для выбранных пользователем команд.
     */
    private void buildChosenDoubleStacks() {
        for (String team : forecastsByTeam.keySet()) {
            if (chosenTeams.contains(team)) {
                List<PlayerForecast> teamList = forecastsByTeam.get(team);
                chosenDoubleStacks.addAll(buildTeamDoubleStacks(teamList));
            }
        }
    }

    /**
     * Построение дабл-стеков команды.
     * @param teamList - список игроков команды.
     * @return - список всех дабл-стеков команды.
     */
    private List<DoubleStack> buildTeamDoubleStacks(List<PlayerForecast> teamList) {

        List<DoubleStack> result = new ArrayList<>();

        for (int i = 0; i < teamList.size() - 1; ++i) {
            for (int j = i + 1; j < teamList.size(); ++j) {
                result.add(new DoubleStack(teamList.get(i), teamList.get(j)));
            }
        }

        return result;
    }

    /**
     * Построение трип-стеков для выбранных пользователем команд.
     */
    private void buildChosenTripleStacks() {
        for (String team : forecastsByTeam.keySet()) {
            if (chosenTeams.contains(team)) {
                List<PlayerForecast> teamList = forecastsByTeam.get(team);
                chosenTripleStacks.addAll(buildTeamTripleStacks(teamList));
            }
        }
    }

    /**
     * Построение трипл-стеков команды.
     * @param teamList - список игроков команды.
     * @return - список всех трипл-стеков команды.
     */
    private List<TripleStack> buildTeamTripleStacks(List<PlayerForecast> teamList) {

        List<TripleStack> result = new ArrayList<>();

        for (int i = 0; i < teamList.size() - 2; ++i) {
            for (int j = i + 1; j < teamList.size() - 1; ++j) {
                for (int k = j + 1; k < teamList.size(); ++k) {
                    result.add(new TripleStack(teamList.get(i), teamList.get(j), teamList.get(k)));
                }
            }
        }

        return result;
    }

    /**
     * Построение списка трипл-стеков и дабл-стеков для команд, не выбранных пользователем.
     */
    private void buildRestStacks() {
        for (String team : forecastsByTeam.keySet()) {
            if (!chosenTeams.contains(team)) {
                restDoubleStacks.addAll(buildTeamDoubleStacks(forecastsByTeam.get(team)));
                restTripleStacks.addAll(buildTeamTripleStacks(forecastsByTeam.get(team)));
            }
        }
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

    public Map<String, List<PlayerForecast>> getForecastsByTeam() {
        return forecastsByTeam;
    }
}