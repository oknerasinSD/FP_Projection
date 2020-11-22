package com.example.fp_predictor.scraping;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ScraperTest {

    @Test
    public void testScrape() throws IOException {
        Scraper scraper = new Scraper();
        scraper.scrape();
    }
}
