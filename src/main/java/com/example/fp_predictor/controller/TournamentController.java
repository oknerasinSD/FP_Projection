package com.example.fp_predictor.controller;

import com.example.fp_predictor.converter.DateConverter;
import com.example.fp_predictor.converter.TimeConverter;
import com.example.fp_predictor.domain.PlayerForecast;
import com.example.fp_predictor.domain.Tournament;
import com.example.fp_predictor.domain.TournamentTeam;
import com.example.fp_predictor.repository.PlayerForecastRepository;
import com.example.fp_predictor.repository.TournamentRepository;
import com.example.fp_predictor.repository.TournamentTeamRepository;
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
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Controller
public class TournamentController {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private PlayerForecastRepository playerForecastRepository;

    @Autowired
    private TournamentTeamRepository tournamentTeamsRepository;

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
            @RequestParam String startDate,
            @RequestParam String startTime,
            @RequestParam String endDate,
            @RequestParam String endTime,
            @RequestParam("file") MultipartFile file,
            /*@RequestParam("image") MultipartFile image,*/
            Map<String, Object> model
    ) throws IOException {

        DateConverter dateConverter = new DateConverter();
        TimeConverter timeConverter = new TimeConverter();
        Date sqlStartDate = dateConverter.toDatabase(startDate);
        Time sqlStartTime = timeConverter.toDatabase(startTime);
        Date sqlEndDate = dateConverter.toDatabase(endDate);
        Time sqlEndTime = timeConverter.toDatabase(endTime);

        Tournament tournament = new Tournament(
                title,
                league,
                sqlStartDate,
                sqlStartTime,
                sqlEndDate,
                sqlEndTime
        );

        /*if (image != null) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + image.getOriginalFilename();
            image.transferTo(new File(uploadPath + File.separator + resultFilename));
            tournament.setFilename(resultFilename);
        }*/

        tournamentRepository.save(tournament);
        parseForecastsAndTeams(file, tournament);

        return "redirect:/";
    }

    private void parseForecastsAndTeams(MultipartFile file, Tournament tournament) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
        Set<String> teams = new HashSet<>();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            teams.add(line[3]);
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
        for (String team : teams) {
            TournamentTeam tournamentTeam = new TournamentTeam(
                    tournament.getId(),
                    team
            );
            tournamentTeamsRepository.save(tournamentTeam);
        }
    }
}
