package com.example.fp_predictor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TournamentController {

    @GetMapping("/addTournament")
    public String open() {
        return "addTournament";
    }
}
