package com.example.fp_predictor.controller;

import com.example.fp_predictor.domain.Tournament;
import com.example.fp_predictor.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TournamentPageController {

    @Autowired
    private TournamentRepository repository;

    @GetMapping("/tournament")
    public String open(@RequestParam("id") long id) {
        Tournament tournament = repository.findById(id);
        return "tournamentPage";
    }
}
