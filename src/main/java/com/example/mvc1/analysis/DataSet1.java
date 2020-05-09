package com.example.mvc1.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class DataSet1 {

    private List<DataInstance1> dataSet = new ArrayList<>();
    private double[] result = new double[20];

    void readDataSet1() throws FileNotFoundException {
        File inputFile = new File("dataset.csv");
        Scanner scanner = new Scanner(inputFile);
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] currentLine = scanner.nextLine().split(",");
            if ("RFPL".equals(currentLine[0])) {
                break;
            }
            DataInstance1 data = new DataInstance1(
                    Integer.parseInt(currentLine[2]),
                    Double.parseDouble(currentLine[12])
            );
            dataSet.add(data);
        }
        scanner.close();
    }

    void analyseByPosition() {
        SortDataSetByPosition();
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

    void SortDataSetByPosition() {
        dataSet.sort(new Comparator<DataInstance1>() {
            @Override
            public int compare(DataInstance1 dataLine1, DataInstance1 dataLine2) {
                return dataLine1.getPosition() - dataLine2.getPosition();
            }
        });
    }

    void writeDataSet1() throws IOException {
        File outputFile = new File("output1.txt");
        FileWriter writer = new FileWriter(outputFile);
        for (int i = 0; i < 20; ++i) {
            writer.write((i + 1) + "\t" + result[i] + "\n");
        }
        writer.close();
    }

    double countDifferencePerGame() {
        double sum = 0;
        for (Double d : result) {
            sum += d;
        }
        return sum / 9130;
    }

    List<DataInstance1> getDataSet() {
        return dataSet;
    }

    public double[] getResult() {
        return result;
    }
}
