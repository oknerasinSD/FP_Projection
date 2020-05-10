package com.example.mvc1.analysis;

import com.example.mvc1.analysis.dataset.DataSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AnalysisTest {

    @DisplayName("File Reading Test")
    @Test
    public void fileReadingTest() throws IOException {
        DataSet dataSet1 = new DataSet();
        dataSet1.readDataSet();
        dataSet1.analyseByPosition();
        dataSet1.writeDataSet1();
        System.out.println(dataSet1.countDifferencePerGame());
    }
}
