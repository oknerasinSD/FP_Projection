package com.example.fp_predictor.optimization.knapsack.dynamic;

import com.example.fp_predictor.analysis.prediction.ExpectedPoints;
import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.domain.TournamentTeam;
import com.example.fp_predictor.optimization.knapsack.FantasyTeam;
import com.example.fp_predictor.optimization.stacks.Stackable;
import com.example.fp_predictor.optimization.stacks.Stacks;
import com.example.fp_predictor.scraping.League;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

public class MapKeyTest {

    @Test
    public void mapKeyTest() throws IOException {

        List<Player> players = parseFile();
        Stacks stacks = new Stacks(players, League.EPL, Collections.emptySet());
        stacks.build();
        Stackable stackable1 = stacks.getRestTripleStacks().get(0);
        Stackable stackable2 = stacks.getRestTripleStacks().get(0);
        MapKey mapKey1 = new MapKey(stackable1);
        MapKey mapKey2 = new MapKey(stackable2);
        Assertions.assertEquals(mapKey1, mapKey2);
        mapKey2.addStackable(stacks.getRestTripleStacks().get(1));
        Assertions.assertNotEquals(mapKey1, mapKey2);

        /*MatrixElement matrixElement = new MatrixElement();
        FantasyTeam fantasyTeam = new FantasyTeam(stackable1, 1);
        Assertions.assertEquals(matrixElement.put(mapKey1, fantasyTeam), fantasyTeam);*/
    }

    private List<Player> parseFile() throws IOException {
        FileReader reader = new FileReader("PredictionOutput.csv");
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
        List<Player> result = new ArrayList<>();
        long fanteam_id = 0;
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
