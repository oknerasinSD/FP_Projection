package com.example.fp_predictor.controller;

import com.example.fp_predictor.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String open(Model model) {
        model.addAttribute("tournaments", tournamentRepository.findAll());
        return "main";
    }
}