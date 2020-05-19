package com.example.fp_predictor.analysis.prediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Estimation {

    private List<EstimationInstance> result = new ArrayList<>();

    public void readDataSet() throws FileNotFoundException {
        File results = new File("Results.txt");
        File predictions = new File("PredictionOutput.txt");
        Scanner resultScanner = new Scanner(results);
        Scanner predictionScanner = new Scanner(predictions);
        while (resultScanner.hasNext()) {
            String[] resultLine = resultScanner.nextLine().split("\t");
            String[] predictionLine = predictionScanner.nextLine().split("\t");
            result.add(new EstimationInstance(
                    resultLine[0],
                    Double.parseDouble(predictionLine[1]),
                    Double.parseDouble(resultLine[1])
            ));
        }
        resultScanner.close();
    }

    public void countDifference() {
        int count = 0;
        for (EstimationInstance instance : result) {
            instance.setDifference(instance.getResultPoints() - instance.getExpectedPoints());
        }
    }

    public double countMAPE() {
        countDifference();
        double sumRelativeError = 0;
        int count = 0;
        for (EstimationInstance instance : result) {
            if (instance.getResultPoints() != 0) {
                ++count;
                sumRelativeError += instance.getDifference() / instance.getResultPoints();
            }
        }
        return Math.abs(sumRelativeError / count) * 100;
    }
}
