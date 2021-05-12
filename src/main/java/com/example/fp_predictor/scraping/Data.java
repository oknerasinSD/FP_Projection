package com.example.fp_predictor.scraping;

import java.util.Map;

public class Data {

    private final Map<String, String> eplLinks = Map.ofEntries(
            Map.entry("ARS", "https://fbref.com/en/squads/18bb7c10/Arsenal-Stats"),
            Map.entry("AV", "https://fbref.com/en/squads/8602292d/Aston-Villa-Stats"),
            Map.entry("BHA", "https://fbref.com/en/squads/d07537b9/Brighton-and-Hove-Albion-Stats"),
            Map.entry("BUR", "https://fbref.com/en/squads/943e8050/Burnley-Stats"),
            Map.entry("CHE", "https://fbref.com/en/squads/cff3d9bb/Chelsea-Stats"),
            Map.entry("CRY", "https://fbref.com/en/squads/47c64c55/Crystal-Palace-Stats"),
            Map.entry("EVE", "https://fbref.com/en/squads/d3fd31cc/Everton-Stats"),
            Map.entry("FUL", "https://fbref.com/en/squads/fd962109/Fulham-Stats"),
            Map.entry("LEE", "https://fbref.com/en/squads/5bfb9659/Leeds-United-Stats"),
            Map.entry("LEI", "https://fbref.com/en/squads/a2d435b3/Leicester-City-Stats"),
            Map.entry("LIV", "https://fbref.com/en/squads/822bd0ba/Liverpool-Stats"),
            Map.entry("MCI", "https://fbref.com/en/squads/b8fd03ef/Manchester-City-Stats"),
            Map.entry("MUN", "https://fbref.com/en/squads/19538871/Manchester-United-Stats"),
            Map.entry("NEW", "https://fbref.com/en/squads/b2b47a98/Newcastle-United-Stats"),
            Map.entry("SHU", "https://fbref.com/en/squads/1df6b87e/Sheffield-United-Stats"),
            Map.entry("SOU", "https://fbref.com/en/squads/33c895d4/Southampton-Stats"),
            Map.entry("TOT", "https://fbref.com/en/squads/361ca564/Tottenham-Hotspur-Stats"),
            Map.entry("WBA", "https://fbref.com/en/squads/60c6b05f/West-Bromwich-Albion-Stats"),
            Map.entry("WHU", "https://fbref.com/en/squads/7c21e445/West-Ham-United-Stats"),
            Map.entry("WOL", "https://fbref.com/en/squads/8cec06e1/Wolverhampton-Wanderers-Stats")
    );

    private final Map<String, String> serieA_links = Map.ofEntries(
            Map.entry("ATA", "https://fbref.com/en/squads/922493f3/Atalanta-Stats"),
            Map.entry("BEN", "https://fbref.com/en/squads/4fcb34fd/Benevento-Stats"),
            Map.entry("BOL", "https://fbref.com/en/squads/1d8099f8/Bologna-Stats"),
            Map.entry("CAG", "https://fbref.com/en/squads/c4260e09/Cagliari-Stats"),
            Map.entry("CRO", "https://fbref.com/en/squads/3074d7b1/Crotone-Stats"),
            Map.entry("FIO", "https://fbref.com/en/squads/421387cf/Fiorentina-Stats"),
            Map.entry("GEN", "https://fbref.com/en/squads/658bf2de/Genoa-Stats"),
            Map.entry("VER", "https://fbref.com/en/squads/0e72edf2/Hellas-Verona-Stats"),
            Map.entry("INT", "https://fbref.com/en/squads/d609edc0/Internazionale-Stats"),
            Map.entry("JUV", "https://fbref.com/en/squads/e0652b02/Juventus-Stats"),
            Map.entry("LAZ", "https://fbref.com/en/squads/7213da33/Lazio-Stats"),
            Map.entry("ACM", "https://fbref.com/en/squads/dc56fe14/Milan-Stats"),
            Map.entry("NAP", "https://fbref.com/en/squads/d48ad4ff/Napoli-Stats"),
            Map.entry("PAR", "https://fbref.com/en/squads/eab4234c/Parma-Stats"),
            Map.entry("ROM", "https://fbref.com/en/squads/cf74a709/Roma-Stats"),
            Map.entry("SAM", "https://fbref.com/en/squads/8ff9e3b3/Sampdoria-Stats"),
            Map.entry("SAS", "https://fbref.com/en/squads/e2befd26/Sassuolo-Stats"),
            Map.entry("SPE", "https://fbref.com/en/squads/68449f6d/Spezia-Stats"),
            Map.entry("TOR", "https://fbref.com/en/squads/105360fe/Torino-Stats"),
            Map.entry("UDI", "https://fbref.com/en/squads/04eea015/Udinese-Stats")
    );

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
            Map.entry("yellowCards", "td.right:nth-of-type(13)"),
            Map.entry("redCards", "td.right:nth-of-type(14)"),
            Map.entry("totalXg", "td.group_start.right:nth-of-type(20)"),
            Map.entry("totalXa", "td.right:nth-of-type(22)"),
            Map.entry("xg90", "td.group_start.right:nth-of-type(24)"),
            Map.entry("xa90", "td.right:nth-of-type(25)")
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

    public Map<String, String> getEplLinks() {
        return eplLinks;
    }

    public Map<String, String> getSerieA_links() {
        return serieA_links;
    }

    public Map<String, String> getLaLiga() {
        return null;
    }
}
