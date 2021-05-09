package com.example.fp_predictor.controller;

import com.example.fp_predictor.converter.DateConverter;
import com.example.fp_predictor.converter.TimeConverter;
import com.example.fp_predictor.domain.Tournament;
import com.example.fp_predictor.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;

@Controller
public class TournamentController {

    @Autowired
    private TournamentRepository repository;

    @GetMapping("/addTournament")
    public String open() {
        return "addTournament";
    }

    @PostMapping("/addTournament")
    public String add(
            @RequestParam String title,
            @RequestParam String league,
            /*@RequestParam String startDate,
            @RequestParam String startTime,
            @RequestParam String endDate,
            @RequestParam String endTime,*/
            Map<String, Object> model
    ) {

        /*DateConverter dateConverter = new DateConverter();
        TimeConverter timeConverter = new TimeConverter();
        Date sqlStartDate = dateConverter.toDatabase(startDate);
        Time sqlStartTime = timeConverter.toDatabase(startTime);
        Date sqlEndDate = dateConverter.toDatabase(endDate);
        Time sqlEndTime = timeConverter.toDatabase(endTime);*/

        Tournament tournament = new Tournament(
                title,
                league
        );
        repository.save(tournament);

        return "main";
    }
}
