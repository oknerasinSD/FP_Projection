package com.example.mvc1.analysis.prediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpectedPoints {

    private List<DataInstance> dataSet = new ArrayList<>();
    private List<PredictionInstance> result = new ArrayList<>();
    private double totalTimePlayedInTournament = 2550;

    public void readDataSet() throws FileNotFoundException {
        File file = new File("PredictionInput.txt");
        Scanner scanner = new Scanner(file);
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] currentLine = scanner.nextLine().split(",");
            DataInstance dataInstance = new DataInstance(
                    currentLine[0],
                    Double.parseDouble(currentLine[1]),
                    Double.parseDouble(currentLine[2]),
                    Double.parseDouble(currentLine[3]),
                    Integer.parseInt(currentLine[4]),
                    Double.parseDouble(currentLine[5]),
                    Double.parseDouble(currentLine[6]),
                    currentLine[7],
                    Integer.parseInt(currentLine[8]),
                    Double.parseDouble(currentLine[9]),
                    Double.parseDouble(currentLine[10]),
                    Integer.parseInt(currentLine[11]),
                    Integer.parseInt(currentLine[12]),
                    Integer.parseInt(currentLine[13])
            );
            dataSet.add(dataInstance);
        }
        scanner.close();
    }

    /**
     * Расчет ожидаемых очков игрока на ближайший матч.
     * @param dataInstance - данные со необходимой для вычислений статистикой.
     */
    public double countPlayerPoints(DataInstance dataInstance) {
        FanTeamScoring fanTeamScoring = new FanTeamScoring();
        double result = fanTeamScoring.appearance + fanTeamScoring.play60minutes;
        /*
        Вычисление ожидаемых очков за результативные действия и матч без пропущенных мячей.
         */
        double timeShare = dataInstance.getMinutesPlayed() / totalTimePlayedInTournament;
        double xGShare = dataInstance.getPlayerXG() / (dataInstance.getTeamXG() * timeShare);
        double xAShare = dataInstance.getPlayerXA() / (dataInstance.getTeamXG() * timeShare);
        double expectedTeamGoals = -Math.log(1 - dataInstance.getTeamToScoreProbability());
        double expectedPlayerGoals = xGShare * expectedTeamGoals;
        double expectedPlayersAssists = xAShare * expectedTeamGoals;
        result += expectedPlayersAssists * fanTeamScoring.assist;
        switch (dataInstance.getPosition()) {
            case "fw":
                result += expectedPlayerGoals * fanTeamScoring.forwardGoal;
                break;
            case "mid":
                result += expectedPlayerGoals * fanTeamScoring.midfielderGoal;
                result += dataInstance.getCleanSheetProbability() * fanTeamScoring.midfielderCleanSheet;
                break;
            case "def":
                result += expectedPlayerGoals * fanTeamScoring.defenderGoal;
                result += dataInstance.getCleanSheetProbability() * fanTeamScoring.cleanSheet;
                break;
        }
        /*
        Вычисление ожидаемых очков за карточки.
         */
        double yellowCardsPerMinute = (double) dataInstance.getYellowCards() / dataInstance.getMinutesPlayed();
        double redCardsPerMinute = (double) dataInstance.getRedCards() / dataInstance.getMinutesPlayed();
        result += (yellowCardsPerMinute * fanTeamScoring.yellowCard + redCardsPerMinute * fanTeamScoring.redCard);
        /*
        Вычисление ожидаемых очков за импакт.
         */
        double expectedImpact = dataInstance.getWinningProbability() * fanTeamScoring.positiveImpact +
                dataInstance.getLoosingProbability() * fanTeamScoring.negativeImpact;
        result += expectedImpact;

        return result;
    }

    public void countExpectedPoints() {
        for (DataInstance dataInstance : dataSet) {
            result.add(new PredictionInstance(dataInstance.getPlayerName(), countPlayerPoints(dataInstance)));
        }
    }

    public void writeData() throws IOException {
        File outputFile = new File("PredictionOutput.txt");
        FileWriter writer = new FileWriter(outputFile);
        for (PredictionInstance resultInstance : result) {
            writer.write(resultInstance.getPlayerName() + "\t" + resultInstance.getExpectedPoints() + "\n");
        }
        writer.close();
    }

    public List<DataInstance> getDataSet() {
        return dataSet;
    }

    public List<PredictionInstance> getResult() {
        return result;
    }

    public double getTotalTimePlayedInTournament() {
        return totalTimePlayedInTournament;
    }
}
