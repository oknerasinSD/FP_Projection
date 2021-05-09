package com.example.fp_predictor.controller;

import com.example.fp_predictor.repository.TournamentTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class TournamentPageController {

    @Autowired
    private TournamentTeamRepository tournamentTeamRepository;

    @GetMapping("/tournament")
    public String open(@RequestParam("id") long id, Model model) {
        List<String> teams = tournamentTeamRepository.findByTournamentId(id);
        Collections.sort(teams);
        model.addAttribute("teams", teams);
        return "tournamentPage";
    }
}
