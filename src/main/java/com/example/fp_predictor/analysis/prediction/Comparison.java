package com.example.fp_predictor.analysis.prediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для обработки сравнения ожидаемых фэнтези-очков игроков с фактическими.
 */
public class Comparison {

    /** Список с результатами сравнения. */
    private List<ComparisonResultInstance> result = new ArrayList<>();

    /**
     * Считывание файла с данными для анализа.
     * @throws FileNotFoundException - файл не найден.
     */
    public void readDataSet() throws FileNotFoundException {
        File results = new File("Results.txt");
        File predictions = new File("PredictionOutput.txt");
        Scanner resultScanner = new Scanner(results);
        Scanner predictionScanner = new Scanner(predictions);
        while (resultScanner.hasNext()) {
            String[] resultLine = resultScanner.nextLine().split("\t");
            String[] predictionLine = predictionScanner.nextLine().split("\t");
            result.add(new ComparisonResultInstance(
                    resultLine[0],
                    Double.parseDouble(predictionLine[1]),
                    Double.parseDouble(resultLine[1])
            ));
        }
        resultScanner.close();
    }

    /**
     * Вычисление разницы между фактическими и ожидаемыми очками.
     */
    public void countDifference() {
        int count = 0;
        for (ComparisonResultInstance instance : result) {
            instance.setDifference(instance.getResultPoints() - instance.getExpectedPoints());
        }
    }

    /**
     * Вычисление среднего абсолютного процентного отклонения ожидаемых очков от фактических (MAPE).
     * @return - MAPE.
     */
    public double countMAPE() {
        countDifference();
        double sumRelativeError = 0;
        int count = 0;
        for (ComparisonResultInstance instance : result) {
            if (instance.getResultPoints() != 0) {
                ++count;
                sumRelativeError += instance.getDifference() / instance.getResultPoints();
                writeToDb(count, instance);
            }
        }
        return Math.abs(sumRelativeError / count) * 100;
    }

    /**
     * Запись результата в БД.
     * @param count - порядковый номер игрока в списке.
     * @param instance - объект с данными для записи.
     */
    private void writeToDb(int count, ComparisonResultInstance instance) {
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
                                    + instance.getName()
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
