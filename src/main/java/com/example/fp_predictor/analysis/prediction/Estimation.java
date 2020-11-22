package com.example.fp_predictor.analysis.prediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
                writeToDb(count, instance);
            }
        }
        return Math.abs(sumRelativeError / count) * 100;
    }

    private void writeToDb(int count, EstimationInstance instance) {
        try {
            String url = "jdbc:postgresql://localhost/fp_predictor";
            String username = "postgres";
            String password = "postgrespass";
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                try {
                    statement.executeUpdate(
                            "INSERT into player values ("
                                    + count + ", '"
                                    + instance.getPlayerName()
                                    + "', " + instance.getExpectedPoints()
                                    + ")"
                    );
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
