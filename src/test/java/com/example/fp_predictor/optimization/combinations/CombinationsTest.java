package com.example.fp_predictor.optimization.combinations;

import com.example.fp_predictor.analysis.prediction.ExpectedPoints;
import com.example.fp_predictor.optimization.combinations.stacks.DoubleStack;
import com.example.fp_predictor.optimization.combinations.stacks.TripleStack;
import com.example.fp_predictor.scraping.League;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CombinationsTest {

    @Test
    public void testCombinationsBuilding() throws IOException {

        Combinations combinations = new Combinations(new ExpectedPoints().count(), League.EPL);
        combinations.build();

        for (String team : combinations.getDoubleStacksByTeam().keySet()) {
            System.out.println(team);
            System.out.println();
            for (DoubleStack stack : combinations.getDoubleStacksByTeam().get(team)) {
                System.out.println(stack);
            }
            System.out.println();
        }

        for (String team : combinations.getTripleStacksByTeam().keySet()) {
            System.out.println(team);
            System.out.println();
            for (TripleStack stack : combinations.getTripleStacksByTeam().get(team)) {
                System.out.println(stack);
            }
            System.out.println();
        }
    }
}
