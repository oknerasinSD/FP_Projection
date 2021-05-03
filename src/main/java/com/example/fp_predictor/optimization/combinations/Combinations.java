package com.example.fp_predictor.optimization.combinations;

import com.example.fp_predictor.analysis.prediction.PlayerForecast;
import com.example.fp_predictor.optimization.combinations.data.Teams;
import com.example.fp_predictor.optimization.combinations.stacks.DoubleStack;
import com.example.fp_predictor.optimization.combinations.stacks.TripleStack;
import com.example.fp_predictor.scraping.League;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для построения стеков.
 */
public class Combinations {

    /** Лига, для команд которой строятся стеки. */
    private final League league;

    /** Список игроков, из которого собираются стеки. */
    private List<PlayerForecast> forecasts;

    /** Мапа вида <Команда : Список дабл-стеков команды>. */
    private Map<String, List<DoubleStack>> doubleStacksByTeam = new HashMap<>();

    /** Мапа вида <Команда : Список трипл-стеков команды>. */
    private Map<String, List<TripleStack>> tripleStacksByTeam = new HashMap<>();

    /** Мапа вида <Команда : Список игроков команды>. */
    private Map<String, List<PlayerForecast>> forecastsByTeam = new HashMap<>();


    public Combinations(List<PlayerForecast> forecasts, League league) {
        this.forecasts = forecasts;
        this.league = league;
    }

    /**
     * Построение всех стеков.
     */
    public void build() {
        buildForecastsByTeam(league);
        buildDoubleStacksByTeam();
        buildTripleStacksByTeam();
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
     * Построение дабл-стеков для команд.
     */
    public void buildDoubleStacksByTeam() {
        for (String team : forecastsByTeam.keySet()) {
            List<PlayerForecast> teamList = forecastsByTeam.get(team);
            List<DoubleStack> teamDoubleStacks = new ArrayList<>();
            for (int i = 0; i < teamList.size() - 1; ++i) {
                for (int j = i + 1; j < teamList.size(); ++j) {
                    teamDoubleStacks.add(new DoubleStack(teamList.get(i), teamList.get(j)));
                }
            }
            doubleStacksByTeam.put(team, teamDoubleStacks);
        }
    }

    /**
     * Построение трип-стеков для команд.
     */
    public void buildTripleStacksByTeam() {
        for (String team : forecastsByTeam.keySet()) {
            List<PlayerForecast> teamList = forecastsByTeam.get(team);
            List<TripleStack> teamTripleStacks = new ArrayList<>();
            for (int i = 0; i < teamList.size() - 2; ++i) {
                for(int j = i + 1; j < teamList.size() - 1; ++j) {
                    for (int k = j + 1; k < teamList.size(); ++k) {
                        teamTripleStacks.add(new TripleStack(teamList.get(i), teamList.get(j), teamList.get(k)));
                    }
                }
            }
            tripleStacksByTeam.put(team, teamTripleStacks);
        }
    }

    public League getLeague() {
        return league;
    }

    public List<PlayerForecast> getForecasts() {
        return forecasts;
    }

    public Map<String, List<DoubleStack>> getDoubleStacksByTeam() {
        return doubleStacksByTeam;
    }

    public Map<String, List<TripleStack>> getTripleStacksByTeam() {
        return tripleStacksByTeam;
    }

    public Map<String, List<PlayerForecast>> getForecastsByTeam() {
        return forecastsByTeam;
    }
}
