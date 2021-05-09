package com.example.fp_predictor.controller;

import com.example.fp_predictor.domain.PlayerForecast;
import com.example.fp_predictor.domain.Tournament;
import com.example.fp_predictor.repository.PlayerForecastRepository;
import com.example.fp_predictor.repository.TournamentRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Controller
public class TournamentController {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private PlayerForecastRepository playerForecastRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/addTournament")
    public String open() {
        return "addTournament";
    }

    @PostMapping("/addTournament")
    public String add(
            @RequestParam String title,
            @RequestParam String league,
            @RequestParam("file") MultipartFile file,
            Map<String, Object> model
    ) throws IOException {

        Tournament tournament = new Tournament(
                title,
                league
        );
        tournamentRepository.save(tournament);

        List<PlayerForecast> forecasts = new ArrayList<>();
        Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            PlayerForecast forecast = new PlayerForecast(
                    tournament.getId(),
                    Long.parseLong(line[0]),
                    Long.parseLong(line[1]),
                    line[2],
                    line[3],
                    line[4],
                    Double.parseDouble(line[5]),
                    Double.parseDouble(line[6])
            );
            playerForecastRepository.save(forecast);
        }

        return "redirect:/";
    }
}
