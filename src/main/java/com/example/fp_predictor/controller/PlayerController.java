package com.example.fp_predictor.controller;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public String open(Model model) {
        Iterable<Player> players = playerRepository.findAll();
        model.addAttribute("players", players);
        return "players";
    }
}
