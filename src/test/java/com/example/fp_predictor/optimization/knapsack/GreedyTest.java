package com.example.fp_predictor.optimization.knapsack;

import com.example.fp_predictor.analysis.prediction.ExpectedPoints;
import com.example.fp_predictor.analysis.prediction.PlayerForecast;
import com.example.fp_predictor.scraping.League;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class GreedyTest {

    @Test
    public void greedyTest() throws IOException {

        Set<String> teams4 = new HashSet<>();
        teams4.add("LEE");
        teams4.add("CHE");
        teams4.add("AV");
        teams4.add("ARS");

        Set<String> teams1 = new HashSet<>();
        teams1.add("CHE");

        Set<String> teams2 = new HashSet<>();
        teams2.add("TOT");
        teams2.add("LIV");

        Set<String> teams3 = new HashSet<>();
        teams3.add("CHE");
        teams3.add("LIV");
        teams3.add("ARS");

        ExpectedPoints expectedPoints = new ExpectedPoints(35);
        List<PlayerForecast> players = expectedPoints.count();
        Greedy greedy = new Greedy(players, League.EPL, teams3, expectedPoints.getTournamentId());
        greedy.solve();
        System.out.println(greedy.getFinalTeam());
        greedy.getFinalTeam().createFanTeamInputFile();
    }
}
