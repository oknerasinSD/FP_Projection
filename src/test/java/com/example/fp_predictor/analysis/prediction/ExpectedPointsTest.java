package com.example.fp_predictor.analysis.prediction;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExpectedPointsTest {

    @Test
    public void countTest() throws IOException {
        ExpectedPoints expectedPoints = new ExpectedPoints(35);
        expectedPoints.count();
        expectedPoints.writeData();
    }
}
