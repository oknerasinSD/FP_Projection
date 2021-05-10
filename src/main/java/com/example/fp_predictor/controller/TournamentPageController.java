package com.example.fp_predictor.controller;

import com.example.fp_predictor.domain.Player;
import com.example.fp_predictor.domain.Tournament;
import com.example.fp_predictor.exeptions.UnknownLeagueException;
import com.example.fp_predictor.optimization.knapsack.FantasyTeam;
import com.example.fp_predictor.optimization.knapsack.Greedy;
import com.example.fp_predictor.optimization.stacks.TripleStack;
import com.example.fp_predictor.repository.PlayerRepository;
import com.example.fp_predictor.repository.TournamentRepository;
import com.example.fp_predictor.repository.TournamentTeamRepository;
import com.example.fp_predictor.scraping.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Controller
public class TournamentPageController {

    @Autowired
    private TournamentTeamRepository tournamentTeamRepository;

    @Autowired
    private PlayerRepository playerForecastRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/tournament")
    public String open(@RequestParam("id") long id, Model model) {
        List<String> teams = tournamentTeamRepository.findByTournamentId(id);
        Collections.sort(teams);
        model.addAttribute("teams", teams);
        return "tournamentPage";
    }

    @PostMapping("/tournament")
    public String count(
            @RequestParam("id") long id,
            @RequestParam(required = false) String team1,
            @RequestParam(required = false) String team2,
            @RequestParam(required = false) String team3,
            @RequestParam(required = false) String team4,
            Model model
    ) throws IOException {
        Tournament tournament = tournamentRepository.findById(id);
        Set<String> teams = new HashSet<>();
        if (team1 != null) {
            teams.add(team1);
        }
        if (team2 != null) {
            teams.add(team2);
        }
        if (team3 != null) {
            teams.add(team3);
        }
        if (team4 != null) {
            teams.add(team4);
        }
        Greedy greedy = new Greedy(
                playerForecastRepository.findByTournamentId(id),
                getLeague(tournament),
                teams,
                tournament.getFanteam_id()
        );
        greedy.solve();
        FantasyTeam finalTeam = greedy.getFinalTeam();
        List<Player> players = extractPlayersByPositions(finalTeam);
        String filename = createCsvFile(players, tournament.getFanteam_id());
        model.addAttribute("players", players);
        model.addAttribute("file", filename);
        return "resultTeam";
    }

    private String createCsvFile(List<Player> players, long id) throws IOException {
        String filename = uploadPath + File.separator + UUID.randomUUID().toString() + ".csv";
        File csvOutput = new File(filename);
        if (!csvOutput.exists()) {
            csvOutput.createNewFile();
        }
        FileWriter writer = new FileWriter(csvOutput);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(",");
        for (Player player : players) {
            stringBuilder.append(player.getFanteamPlayerId()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        writer.write(stringBuilder.toString());
        writer.close();
        return filename;
    }

    private List<Player> extractPlayersByPositions(FantasyTeam team) {
        Map<String, List<Player>> playersByPositionMap = new HashMap<>(Map.of(
                "goalkeeper", new ArrayList<>(),
                "defender", new ArrayList<>(),
                "midfielder", new ArrayList<>(),
                "forward", new ArrayList<>()
        )
        );
        for (TripleStack stack : team.getTripleStacks()) {
            for (Player stackPlayer : stack.getPlayers()) {
                playersByPositionMap.get(stackPlayer.getPosition()).add(stackPlayer);
            }
        }
        for (Player stackPlayer : team.getDoubleStacks().get(0).getPlayers()) {
            playersByPositionMap.get(stackPlayer.getPosition()).add(stackPlayer);
        }
        List<Player> playersByPositionList = new ArrayList<>();
        playersByPositionList.addAll(playersByPositionMap.get("goalkeeper"));
        playersByPositionList.addAll(playersByPositionMap.get("defender"));
        playersByPositionList.addAll(playersByPositionMap.get("midfielder"));
        playersByPositionList.addAll(playersByPositionMap.get("forward"));
        return playersByPositionList;
    }

    private League getLeague(Tournament tournament) {
        switch (tournament.getLeague()) {
            case "Англия: АПЛ":
                return League.EPL;
            case "Испания: Ла Лига":
                return League.LA_LIGA;
            case "Италия: Серия А":
                return League.SERIE_A;
            default:
                throw new UnknownLeagueException();
        }
    }
}
