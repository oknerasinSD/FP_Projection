package com.example.fp_predictor.analysis.xg_difference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


/**
 * Анализ выборки, состоящей из статистики выступлений команд чемпионатов Англии, Испании, Германии, Италии и Франции
 * в период с 2014 по 2018 годы, на предмет разницы между количеством ожидаемых голов согласно модели xG и фактическим
 * количеством голов.
 *
 * Цель - выявление зависимости между итоговыми позициями команд и расхождением между ожидаемыми и фактическими голами.
 */
public class Analysis {

    /** Список данных для анализа. */
    private List<DataInstance> dataSet = new ArrayList<>();

    /** Результат, равный разнице между ожидаемым и фактическим количеством голов. */
    private double[] result = new double[20];

    /**
     * Считывание списка данных из файла.
     * @throws FileNotFoundException - файл не найден.
     */
    public void readDataSet() throws FileNotFoundException {
        File inputFile = new File("xGDifferenceDataSet.txt");
        Scanner scanner = new Scanner(inputFile);
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] currentLine = scanner.nextLine().split(",");
            if ("RFPL".equals(currentLine[0])) {
                break;
            }
            DataInstance data = new DataInstance(
                    Integer.parseInt(currentLine[2]),
                    Double.parseDouble(currentLine[12]),
                    Integer.parseInt(currentLine[9])
            );
            dataSet.add(data);
        }
        scanner.close();
    }

    /**
     * Выявление зависимости между итоговыми позициями команд и расхождением между ожидаемыми и фактическими голами.
     */
    public void analyseByPosition() {
        sortDataSetByPosition();
        for (int i = 0; i < 18; ++i) {
            for (int j = 0; j < 25; ++j) {
                result[i] += dataSet.get(i * 25 + j).get_xG_difference();
            }
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 20; ++j) {
                result[18 + i] += dataSet.get(450 + i * 20 + j).get_xG_difference();
            }
        }
    }

    /**
     * Сортировка списка данных по значению позиций команд по итогам сезона.
     */
    public void sortDataSetByPosition() {
        dataSet.sort(new Comparator<DataInstance>() {
            @Override
            public int compare(DataInstance dataLine1, DataInstance dataLine2) {
                return dataLine1.getPosition() - dataLine2.getPosition();
            }
        });
    }

    /**
     * Запись результата в выходной файл.
     * @throws IOException - ошибка ввода/вывода.
     */
    public void writeDataSet() throws IOException {
        File outputFile = new File("xGDifferenceResult.txt");
        FileWriter writer = new FileWriter(outputFile);
        for (int i = 0; i < 20; ++i) {
            writer.write((i + 1) + "\t" + result[i] + "\n");
        }
        writer.close();
    }

    /**
     * Вычисление разницы между ожидаемым и фактическим количеством голов в конкретной игре.
     * @return - разница.
     */
    public double countDifferencePerGame() {
        double sum = 0;
        for (Double d : result) {
            sum += d;
        }
        return sum / 9130;
    }

    /**
     * Вычисление среднего абсолютного процентного отклонения ожидаемых голов от фактических (MAPE).
     * @return - MAPE.
     */
    public double countMAPE() {
        double sumRelativeError = 0;
        for (DataInstance instance : dataSet) {
            sumRelativeError += instance.get_xG_difference() / instance.getNumberOfGoals();
        }
        return Math.abs(sumRelativeError / dataSet.size());
    }

    public List<DataInstance> getDataSet() {
        return dataSet;
    }

    public double[] getResult() {
        return result;
    }
}
