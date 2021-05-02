package com.example.fp_predictor.analysis.prediction;

/**
 * Класс для описания объектов, хранящих статистические данные, необходимые для расчета ожидаемых фэнтези-очков.
 */
public class StatsInstance {

    private String playerName;
    private double teamXG;
    private double playerXG;
    private double playerXA;
    private int minutesPlayed;
    private double cleanSheetProbability;
    private double teamToScoreProbability;
    private String position;
    private int goals;
    private double winningProbability;
    private double loosingProbability;
    private int yellowCards;
    private int redCards;
    private int numberOfGames;

    public StatsInstance(String playerName,
                         double teamXG,
                         double playerXG,
                         double playerXA,
                         int minutesPlayed,
                         double cleanSheetProbability,
                         double teamToScoreProbability,
                         String position,
                         int goals,
                         double winningProbability,
                         double loosingProbability,
                         int yellowCards,
                         int redCards,
                         int numberOfGames
    ) {
        this.playerName = playerName;
        this.teamXG = teamXG;
        this.playerXG = playerXG;
        this.playerXA = playerXA;
        this.minutesPlayed = minutesPlayed;
        this.cleanSheetProbability = cleanSheetProbability;
        this.teamToScoreProbability = teamToScoreProbability;
        this.position = position;
        this.goals = goals;
        this.winningProbability = winningProbability;
        this.loosingProbability = loosingProbability;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.numberOfGames = numberOfGames;
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getTeamXG() {
        return teamXG;
    }

    public double getPlayerXG() {
        return playerXG;
    }

    public double getPlayerXA() {
        return playerXA;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public double getCleanSheetProbability() {
        return cleanSheetProbability;
    }

    public double getTeamToScoreProbability() {
        return teamToScoreProbability;
    }

    public String getPosition() {
        return position;
    }

    public int getGoals() {
        return goals;
    }

    public double getWinningProbability() {
        return winningProbability;
    }

    public double getLoosingProbability() {
        return loosingProbability;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }
}
