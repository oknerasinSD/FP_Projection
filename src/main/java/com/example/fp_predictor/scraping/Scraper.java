package com.example.fp_predictor.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Парсер статистики.
 */
public class Scraper {

//    /** Ссылка на таблицу */
//    private static final String TABLE_REFERENCE =
//            "table.min_width tr";

    private static final String TABLE_REFERENCE = "table.stats_table";
    private Map<String, String> links;

    /**
     * Запуск парсинга.
     * @throws IOException - ошибка при построении списка с объектами, содержащими HTML-код таблиц для парсинга.
     */
    public List<ParsedPlayer> scrape() throws IOException {
        Data data = new Data();
        links = data.getEplLinks();
        return parseTables(data);
    }

    /**
     * Парсинг статистики из HTML-кода таблиц.
     * @param data - объект с вспомогательными данными.
     */
    private List<ParsedPlayer> parseTables(Data data) throws IOException {
        List<ParsedPlayer> players = new ArrayList<>();
        for (String team : links.keySet()) {
            Document document = Jsoup.connect(links.get(team)).get();
            Element table = document.select(TABLE_REFERENCE).first();
            Elements rows = table.select("tr");
            for (Element row : rows) {
                String playerName = row.select(data.getColumn("name")).text();
                if (!data.checkNameCorrectness(playerName)) {
                    continue;
                } else if ("Squad Total".equals(playerName)) {
                    break;
                }
                players.add(buildPlayer(data, row, playerName, team));
            }
        }
        return players;
    }

    /**
     * Парсинг объекта типа ParsedPlayer из строки таблицы.
     * @param data - объект с вспомогательными данными;
     * @param row - строка таблицы;
     * @param playerName - имя игрока.
     * @return - экземпляр класса ParsedPlayer.
     */
    private ParsedPlayer buildPlayer(Data data, Element row, String playerName, String team) {

        String value = row.select(data.getColumn("matchesPlayed")).text();
        int matchesPlayed = (int) checkIfEmpty(value);

        value = row.select(data.getColumn("matchesStarted")).text();
        int matchesStarted = (int) checkIfEmpty(value);

        value = row.select(data.getColumn("minutesPlayed")).text();
        int minutesPlayed = (int) checkIfEmpty(value);

        value = row.select(data.getColumn("yellowCards")).text();
        int yellowCards = (int) checkIfEmpty(value);

        value = row.select(data.getColumn("redCards")).text();
        int redCards = (int) checkIfEmpty(value);

        value = row.select(data.getColumn("totalXg")).text();
        double totalXg = checkIfEmpty(value);

        value = row.select(data.getColumn("totalXa")).text();
        double totalXa = checkIfEmpty(value);

        value = row.select(data.getColumn("xg90")).text();
        double xg90 = checkIfEmpty(value);

        value = row.select(data.getColumn("xa90")).text();
        double xa90 = checkIfEmpty(value);

        return new ParsedPlayer(
                playerName,
                team,
                matchesPlayed,
                matchesStarted,
                minutesPlayed,
                yellowCards,
                redCards,
                totalXg,
                totalXa,
                xg90,
                xa90
        );
    }

    /**
     * Проверка является ли строка пустой.
     * @param value - проверяемая строка.
     * @return - 0, если строка пустая; число, если непустая.
     */
    private double checkIfEmpty(String value) {
        value = value.replaceAll(",", "");
        return "".equals(value) ? 0 : Double.parseDouble(value);
    }
}
