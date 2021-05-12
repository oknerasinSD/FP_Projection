package com.example.fp_predictor.optimization.knapsack;

import com.example.fp_predictor.analysis.prediction.ExpectedPoints;
import com.example.fp_predictor.analysis.prediction.PlayerForecast;
import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.optimization.knapsack.greedy.Greedy;
import com.example.fp_predictor.scraping.League;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

        List<Player> players = parseFile(1);
        Greedy greedy = new Greedy(players, League.EPL, Collections.emptySet(), 1);
        greedy.solve();
        System.out.println(greedy.getFinalTeam());
        /*greedy.getFinalTeam().createFanTeamInputFile();*/
    }

    private List<Player> parseFile(long fanteam_id) throws IOException {
        FileReader reader = new FileReader("PredictionOutput.csv");
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
        List<Player> result = new ArrayList<>();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            fanteam_id = Long.parseLong(line[0]);
            Player forecast = new Player(
                    1,
                    fanteam_id,
                    Long.parseLong(line[1]),
                    line[2],
                    line[3],
                    line[4],
                    Double.parseDouble(line[5]),
                    Double.parseDouble(line[6])
            );
            result.add(forecast);
        }
        return result;
    }
}
