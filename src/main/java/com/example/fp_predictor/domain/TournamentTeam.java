package com.example.fp_predictor.domain;

import javax.persistence.*;

@Entity
public class TournamentTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    private long tournamentId;
    private String team;

    public TournamentTeam() {
    }

    public TournamentTeam(long tournamentId, String team) {
        this.tournamentId = tournamentId;
        this.team = team;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
