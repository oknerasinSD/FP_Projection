package com.example.fp_predictor.optimization.stacks;

import com.example.fp_predictor.analysis.prediction.ExpectedPoints;
import com.example.fp_predictor.scraping.League;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

public class StacksTest {

    @Test
    public void testStacksBuilding() throws IOException {

        Stacks stacks = new Stacks(new ExpectedPoints().count(), League.EPL, Collections.emptySet());
        stacks.build();
        Assertions.assertTrue(stacks.getChosenDoubleStacks().isEmpty());
        Assertions.assertTrue(stacks.getChosenTripleStacks().isEmpty());
        Assertions.assertFalse(stacks.getRestDoubleStacks().isEmpty());
        Assertions.assertFalse(stacks.getRestTripleStacks().isEmpty());
    }
}
