package com.example.fp_predictor.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class Data {

    private final String[] eplLinks = {
            "https://fbref.com/en/share/UiTdO", // Arsenal
            "https://fbref.com/en/share/ivKcD", // Aston Villa
            "https://fbref.com/en/share/YGGAO", // Brighton
            "https://fbref.com/en/share/9sylW", // Burnley
            "https://fbref.com/en/share/1fouZ", // Chelsea
            "https://fbref.com/en/share/uslIX", // Crystal Palace
            "https://fbref.com/en/share/xRDQS", // Everton
            "https://fbref.com/en/share/RsLAW",    // Fulham
            "https://fbref.com/en/share/StB3y",  // Leeds
            "https://fbref.com/en/share/oZOYL",    // Leicester
            "https://fbref.com/en/share/8z0j9", // Liverpool
            "https://fbref.com/en/share/zJVPu",   // Man City
            "https://fbref.com/en/share/PfMyi", // Man United
            "https://fbref.com/en/share/DlxnI",  // Newcastle
            "https://fbref.com/en/share/kow2p",  // Sheffield Utd
            "https://fbref.com/en/share/V57xT",   // Southampton
            "https://fbref.com/en/share/u8KRK", // Tottenham
            "https://fbref.com/en/share/ojJNb",  // West Brom
            "https://fbref.com/en/share/Tigzh",   // West Ham
            "https://fbref.com/en/share/2JnAK"  // Wolverhampton
    };

    private final String[] playersTableBannedItems = {
            "ParsedPlayer",
            "Squad Total",
            "Opponent Total",
            ""
    };

    private Map<String, String> itemValueMapping = Map.ofEntries(
            Map.entry("name", "th.left"),
            Map.entry("matchesPlayed", "td.group_start.right:nth-of-type(4)"),
            Map.entry("matchesStarted", "td.right:nth-of-type(5)"),
            Map.entry("minutesPlayed", "td.right:nth-of-type(6)"),
            Map.entry("yellowCards", "td.right:nth-of-type(11)"),
            Map.entry("redCards", "td.iz.right:nth-of-type(12)"),
            Map.entry("totalXg", "td.group_start.right:nth-of-type(18)"),
            Map.entry("totalXa", "td.right:nth-of-type(20)"),
            Map.entry("xg90", "td.group_start.right:nth-of-type(21)"),
            Map.entry("xa90", "td.right:nth-of-type(22)")
    );

    public String getColumn(String item) {
        return itemValueMapping.get(item);
    }

    public boolean checkNameCorrectness(String name) {
        for (String item : playersTableBannedItems) {
            if (item.equals(name)) {
                return false;
            }
        }
        return true;
    }

    public List<Document> buildDocumentsList(League league) throws IOException {
        List<Document> documents = new ArrayList<>();
        for (String link : eplLinks) {
            documents.add(Jsoup.connect(link).get());
        }
        return documents;
    }
}
