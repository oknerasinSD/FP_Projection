package com.example.fp_predictor.analysis.xg_difference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Analysis {

    private List<DataInstance> dataSet = new ArrayList<>();
    private double[] result = new double[20];

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
                    Double.parseDouble(currentLine[12])
            );
            dataSet.add(data);
        }
        scanner.close();
    }

    public void analyseByPosition() {
        sortDataSetByPosition();
        for (int i = 0; i < 18; ++i) {
            for (int j = 0; j < 25; ++j) {
                result[i] += dataSet.get(i * 25 + j).getxG_difference();
            }
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 20; ++j) {
                result[18 + i] += dataSet.get(450 + i * 20 + j).getxG_difference();
            }
        }
    }

    public void sortDataSetByPosition() {
        dataSet.sort(new Comparator<DataInstance>() {
            @Override
            public int compare(DataInstance dataLine1, DataInstance dataLine2) {
                return dataLine1.getPosition() - dataLine2.getPosition();
            }
        });
    }

    public void writeDataSet() throws IOException {
        File outputFile = new File("xGDifferenceResult.txt");
        FileWriter writer = new FileWriter(outputFile);
        for (int i = 0; i < 20; ++i) {
            writer.write((i + 1) + "\t" + result[i] + "\n");
        }
        writer.close();
    }

    public double countDifferencePerGame() {
        double sum = 0;
        for (Double d : result) {
            sum += d;
        }
        return sum / 9130;
    }

    public List<DataInstance> getDataSet() {
        return dataSet;
    }

    public double[] getResult() {
        return result;
    }
}
