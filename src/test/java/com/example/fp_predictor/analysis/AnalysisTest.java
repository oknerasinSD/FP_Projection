package com.example.fp_predictor.analysis;

import com.example.fp_predictor.analysis.prediction.Estimation;
import com.example.fp_predictor.analysis.prediction.ExpectedPoints;
import com.example.fp_predictor.analysis.xg_difference.Analysis;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AnalysisTest {

    @DisplayName("XG_difference Test")
    @Test
    public void xg_differenceTest() throws IOException {

        Analysis analysis = new Analysis();
        analysis.readDataSet();
        analysis.analyseByPosition();
        analysis.writeDataSet();
        System.out.println("Kaggle DataSet MAPE = " + analysis.countMAPE() * 100);
    }

    @DisplayName("Prediction Test")
    @Test
    public void predictionTest() throws IOException {

        ExpectedPoints expectedPoints = new ExpectedPoints();
        expectedPoints.readDataSet();
        expectedPoints.countExpectedPoints();
        expectedPoints.writeData();

        Estimation estimation = new Estimation();
        estimation.readDataSet();
        System.out.printf("Model MAPE = %.2f", estimation.countMAPE());
    }
}
