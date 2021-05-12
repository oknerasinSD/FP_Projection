package com.example.fp_predictor.analysis.prediction;

import com.example.fp_predictor.scraping.League;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExpectedPointsTest {

    @Test
    public void countTest() throws IOException {
        ExpectedPoints expectedPoints = new ExpectedPoints(36, League.SERIE_A);
        expectedPoints.count();
        expectedPoints.writeData();
    }
}
