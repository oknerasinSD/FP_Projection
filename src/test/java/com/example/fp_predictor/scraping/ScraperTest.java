package com.example.fp_predictor.scraping;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ScraperTest {

    @Test
    public void demoScrape() throws IOException {

        Scraper scraper = new Scraper();
        List<ParsedPlayer> players = scraper.scrape(League.EPL);
        for (ParsedPlayer player : players) {
            System.out.println(player.toString());
        }
    }
}
