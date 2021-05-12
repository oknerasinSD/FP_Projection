package com.example.fp_predictor.optimization.stacks.data;

import com.example.fp_predictor.scraping.League;

import java.util.Arrays;
import java.util.List;

public class Teams {

    private List<String> eplTeams = Arrays.asList(
            "ARS",
            "AV",
            "BHA",
            "BUR",
            "CHE",
            "CRY",
            "EVE",
            "FUL",
            "LEE",
            "LEI",
            "LIV",
            "MCI",
            "MUN",
            "NEW",
            "SHU",
            "SOU",
            "TOT",
            "WBA",
            "WHU",
            "WOL"
    );

    private List<String> serieA_teams = Arrays.asList(
            "ATA",
            "BEN",
            "BOL",
            "CAG",
            "CRO",
            "FIO",
            "GEN",
            "VER",
            "INT",
            "JUV",
            "LAZ",
            "ACM",
            "NAP",
            "PAR",
            "ROM",
            "SAM",
            "SAS",
            "SPE",
            "TOR",
            "UDI"
    );

    private List<String> laLigaTeams;

    public List<String> getTeams(League league) {
        switch (league) {
            case EPL:
                return eplTeams;
            case LA_LIGA:
                return laLigaTeams;
            case SERIE_A:
                return serieA_teams;
            default:
                return null;
        }
    }
}
