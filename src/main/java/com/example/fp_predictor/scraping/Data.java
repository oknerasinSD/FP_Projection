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
}
