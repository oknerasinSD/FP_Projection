package com.example.fp_predictor.scraping;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class Scraper {

    private static final String tableReference =
            "table.min_width tr";

    public void scrape() throws IOException {
        Data data = new Data();
        List<Document> documents = data.buildDocumentsList(League.EPL);
        parseTables(data, documents);
    }

    private void parseTables(Data data, List<Document> documents) {
        for (Document document : documents) {
            for (Element row : document.select(tableReference)) {
                String playerName = row.select(data.getColumn("name")).text();
                if (!data.checkNameCorrectness(playerName)) {
                    continue;
                }
                Player player = buildPlayer(data, row, playerName);
                System.out.println(player);
            }
        }
    }

    private Player buildPlayer(Data data, Element row, String playerName) {

        String value = row.select(data.getColumn("matchesPlayed")).text();
        int matchesPlayed = Integer.parseInt(value);

        value = row.select(data.getColumn("matchesStarted")).text();
        int matchesStarted = Integer.parseInt(value);

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

        return new Player(
                playerName,
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

    private double checkIfEmpty(String value) {
        return "".equals(value) ? 0 : Double.parseDouble(value);
    }
}
