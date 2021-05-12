package com.example.fp_predictor.scraping;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ScraperTest {

    @Test
    public void testScrape() throws IOException {

        Scraper scraper = new Scraper();
        List<ParsedPlayer> players = scraper.scrape(League.SERIE_A);
        for (ParsedPlayer player : players) {
            System.out.println(player.toString());
        }
    }
}
