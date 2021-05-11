package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.analysis.prediction.ExpectedPoints;
import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.scraping.League;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StacksTest {

    @Test
    public void testStacksBuilding() throws IOException {

        List<Player> players = parseFile(1);
        Stacks stacks = new Stacks(players, League.EPL, Collections.emptySet());
        stacks.build();
        Assertions.assertTrue(stacks.getChosenDoubleStacks().isEmpty());
        Assertions.assertTrue(stacks.getChosenTripleStacks().isEmpty());
        Assertions.assertFalse(stacks.getRestDoubleStacks().isEmpty());
        Assertions.assertFalse(stacks.getRestTripleStacks().isEmpty());
    }

    @Test
    public void testFilter() throws IOException {
        List<Player> players = parseFile(1);
        Stacks stacks = new Stacks(players, League.EPL, Collections.emptySet());
        stacks.build();
        for (String team : stacks.getForecastsByTeam().keySet()) {
            System.out.println(team + ":");
            for (Player player : stacks.getForecastsByTeam().get(team)) {
                System.out.println(player);
            }
            System.out.println();
        }
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
