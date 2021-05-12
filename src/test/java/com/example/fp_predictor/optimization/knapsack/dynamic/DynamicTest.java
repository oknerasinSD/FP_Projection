package com.example.fp_predictor.optimization.knapsack.dynamic;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.scraping.League;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DynamicTest {

    @Test
    public void dynamicTest() throws IOException {
        long fanTeamId = 0;
        List<Player> players = parseFile(fanTeamId);
        Set<String> teams = new HashSet<>(Arrays.asList("LIV", "EVE", "WHU", "CRY"));
        Dynamic dynamic = new Dynamic(players, League.EPL, Collections.emptySet(), fanTeamId);
        dynamic.solve();
        System.out.println(dynamic.getFinalTeam());
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
