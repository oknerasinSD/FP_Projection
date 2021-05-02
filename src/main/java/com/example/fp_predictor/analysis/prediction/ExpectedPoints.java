package com.example.fp_predictor.analysis.prediction;

import com.example.fp_predictor.scraping.ParsedPlayer;
import com.example.fp_predictor.scraping.Scraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Класс для подсчета ожидаемых очков.
 */
public class ExpectedPoints {

    /** Игровая неделя. */
    private int gameWeek = 33;

    /** Мапа с общим xG для каждой команды. */
    private final Map<String, Double> teamXgMap = new HashMap<>();

    /** Мапа с вероятностями клиншитов на ближайший матч для каждой команды. */
    private final Map<String, Double> cleanSheetMultipliers = new HashMap<>();

    /** Мапа с вероятностями победы для каждой команды. */
    private final Map<String, Double> winMultipliers = new HashMap<>();

    /** Мапа с вероятностями поражения для каждой команды. */
    private final Map<String, Double> looseMultipliers = new HashMap<>();

    /** Мапа с данными о матчах текущего турнира. */
    private final Map<String, String> opponentsMap = new HashMap<>();

    /** Мапа с коэфициентами на ТБ 0.5. */
    private final Map<String, Double> over05Map = new HashMap<>();

    /** Список с результатами прогноза. */
    private final List<ResultInstance> forecast = new ArrayList<>();

    /**
     * Подсчет ожидаемых очков для всех игроков.
     * @throws IOException - ошибка при парсинге статистики или считывании файлов.
     */
    public void count() throws IOException {

        List<ParsedPlayer> parsedPlayers = new Scraper().scrape();
        List<FanTeamPlayer> fanTeamPlayers = readFanTeamPlayers();
        countTeamXg(parsedPlayers);
        readBookmakersData();
        readOpponentsMap();
        buildLooseMultipliers();

        for (FanTeamPlayer fanTeamPlayer : fanTeamPlayers) {
            for (ParsedPlayer parsedPlayer : parsedPlayers) {
                if (isSamePlayer(fanTeamPlayer, parsedPlayer)) {
                    forecast.add(new ResultInstance(
                                    parsedPlayer.getName(),
                                    fanTeamPlayer.getTeam(),
                                    fanTeamPlayer.getPosition(),
                                    countPlayerPoints(fanTeamPlayer, parsedPlayer)
                            )
                    );
                }
            }
        }
    }

    /**
     * Построение мапы с вероятностью проигрыша.
     */
    private void buildLooseMultipliers() {
        for (String team : winMultipliers.keySet()) {
            looseMultipliers.put(opponentsMap.get(team), winMultipliers.get(team));
        }
    }

    /**
     * Считывание файла с данными о матчах текущего турнира.
     * @throws FileNotFoundException - файл не найден.
     */
    private void readOpponentsMap() throws FileNotFoundException {
        File inputFile = new File("Opponents.txt");
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNext()) {
            String[] currentLine = scanner.nextLine().split(" ");
            opponentsMap.put(currentLine[0], currentLine[1]);
        }
    }

    /**
     * Сравнение объектов "Игрок" из таблицы со статистикой и от фэнтези-првоайдера.
     * @param fanTeamPlayer - объект от фэнтези-провайдера;
     * @param parsedPlayer - объект из таблицы статистикой.
     * @return - TRUE, если это один и тот же реальный игрок; FALSE в противном случае.
     */
    private boolean isSamePlayer(FanTeamPlayer fanTeamPlayer, ParsedPlayer parsedPlayer) {
        return parsedPlayer.getName().contains(fanTeamPlayer.getSurname())
                && parsedPlayer.getName().contains(fanTeamPlayer.getName());
    }

    /**
     * Подсчет общего xG для каждой команды.
     * @param parsedPlayers - объект "Игрок", полученный из таблицы со статистикой;
     */
    private void countTeamXg(List<ParsedPlayer> parsedPlayers) {
        for (ParsedPlayer parsedPlayer : parsedPlayers) {
            if (teamXgMap.containsKey(parsedPlayer.getTeam())) {
                teamXgMap.put(
                        parsedPlayer.getTeam(),
                        teamXgMap.get(parsedPlayer.getTeam()) + parsedPlayer.getTotalXg()
                );
            } else {
                teamXgMap.put(parsedPlayer.getTeam(), parsedPlayer.getTotalXg());
            }
        }
    }

    /**
     * Считывание файла с коэфициентами букмекеров.
     * @throws FileNotFoundException - отсутствует указанный файл.
     */
    private void readBookmakersData() throws FileNotFoundException {
        File inputFile = new File("Bookmakers.txt");
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNext()) {
            String[] currentLine = scanner.nextLine().split(" ");
            cleanSheetMultipliers.put(currentLine[0], Double.valueOf(currentLine[1]));
            winMultipliers.put(currentLine[0], Double.valueOf(currentLine[2]));
            over05Map.put(currentLine[0], Double.valueOf(currentLine[3]));
        }
    }

    /**
     * Подсчет ожидаемых очков игрока.
     */
    public double countPlayerPoints(FanTeamPlayer fanTeamPlayer, ParsedPlayer parsedPlayer) throws IOException {

        FanTeamScoring fanTeamScoring = new FanTeamScoring();
        int totalTimePlayedInTournament = 90 * gameWeek;
        double result = fanTeamScoring.appearance + fanTeamScoring.play60minutes;

        double timeShare = (double) parsedPlayer.getMinutesPlayed() / totalTimePlayedInTournament;
        double xgShare = parsedPlayer.getTotalXg() / teamXgMap.get(fanTeamPlayer.getTeam()) / timeShare;
        double xaShare = parsedPlayer.getTotalXa() / teamXgMap.get(fanTeamPlayer.getTeam()) / timeShare;
        double expectedTeamGoals = -Math.log(1 - 1 / over05Map.get(fanTeamPlayer.getTeam()));
        double expectedPlayerGoals = xgShare * expectedTeamGoals;
        double expectedPlayerAssists = xaShare * expectedTeamGoals;
        double expectedImpact = 1 / winMultipliers.get(fanTeamPlayer.getTeam()) * fanTeamScoring.positiveImpact
                + 1 / looseMultipliers.get(fanTeamPlayer.getTeam()) * fanTeamScoring.negativeImpact;

        result += expectedPlayerAssists * fanTeamScoring.assist;
        result = difPositionsEstimation(fanTeamPlayer, fanTeamScoring, result, expectedPlayerGoals, timeShare);
        result = addBookingEstimation(parsedPlayer, fanTeamScoring, result);
        result += expectedImpact;

        return result;
    }

    /**
     * Оценка штрафных очков за получение карточек.
     * @param parsedPlayer - объект типа ParsedPlayer;
     * @param fanTeamScoring - скоринг;
     * @param result - прогноз без учета штрафа.
     * @return - прогноз с учетом штрафа.
     */
    private double addBookingEstimation(ParsedPlayer parsedPlayer, FanTeamScoring fanTeamScoring, double result) {
        double yellowCards90 = (double) parsedPlayer.getYellowCards() / parsedPlayer.getMinutesPlayed() * 90;
        double redCards90 = (double) parsedPlayer.getRedCards() / parsedPlayer.getMinutesPlayed() * 90;
        result += (yellowCards90 * fanTeamScoring.yellowCard + redCards90 * fanTeamScoring.redCard);
        return result;
    }

    /**
     * Оценка очков за действия, награда за которые зависит от позиции.
     * @param fanTeamPlayer - объект типа FanTeamPlayer;
     * @param fanTeamScoring - скоринг;
     * @param result - прогноз без учета голевых действий;
     * @param expectedPlayerGoals - общий xG игрока за сезон.
     * @return - прогноз с учетом голевых действий.
     */
    private double difPositionsEstimation(
            FanTeamPlayer fanTeamPlayer,
            FanTeamScoring fanTeamScoring,
            double result,
            double expectedPlayerGoals,
            double timeShare
    ) {
        switch (fanTeamPlayer.getPosition()) {
            case "defender":
                result += expectedPlayerGoals * fanTeamScoring.defenderGoal;
                result += 1 / cleanSheetMultipliers.get(fanTeamPlayer.getTeam()) * fanTeamScoring.defenderCleanSheet;
                break;
            case "midfielder":
                result += expectedPlayerGoals * fanTeamScoring.midfielderGoal;
                result += 1 / cleanSheetMultipliers.get(fanTeamPlayer.getTeam()) * fanTeamScoring.midfielderCleanSheet;
                result += timeShare;
                break;
            case "forward":
                result += expectedPlayerGoals * fanTeamScoring.forwardGoal;
                result += timeShare;
                break;
        }
        return result;
    }

    /**
     * Считывание файла с данными об игроках от фэнтези-провайдера.
     * @return - список объектов типа FanTeamPlayer.
     * @throws FileNotFoundException - файл не найден.
     */
    private List<FanTeamPlayer> readFanTeamPlayers() throws FileNotFoundException {
        List<FanTeamPlayer> result = new ArrayList<>();
        File inputFile = new File("FanTeamPlayers.csv");
        Scanner scanner = new Scanner(inputFile);
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] currentLine = scanner.nextLine().split(",");
            result.add(new FanTeamPlayer(
                    currentLine[3], currentLine[2], currentLine[4], currentLine[6], Double.parseDouble(currentLine[7]))
            );
        }
        return result;
    }

    /**
     * Запись данных в результирующий файл.
     * @throws IOException - ошибка записи данных.
     */
    public void writeData() throws IOException {
        File outputFile = new File("PredictionOutput.txt");
        FileWriter writer = new FileWriter(outputFile);
        for (ResultInstance resultInstance : forecast) {
            writer.write(
                    resultInstance.getName() + "\t"
                            + resultInstance.getTeam() + "\t"
                            + resultInstance.getPosition() + "\t"
                            + resultInstance.getExpectedPoints() + "\n"
            );
        }
        writer.close();
    }
}
