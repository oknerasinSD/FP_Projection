package com.example.fp_predictor.domain;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    private long systemTournamentId;
    private long fanteamTournamentId;
    private long fanteamPlayerId;
    private String name;
    private String team;
    private String position;
    private double expectedPoints;
    private double price;
    /*private int price_x_10;*/

    public Player() {
    }

    public Player(
            long systemTournamentId,
            long fanteamTournamentId,
            long fanteamPlayerId,
            String playerName,
            String team,
            String position,
            double expectedPoints, double price
    ) {
        this.systemTournamentId = systemTournamentId;
        this.fanteamTournamentId = fanteamTournamentId;
        this.fanteamPlayerId = fanteamPlayerId;
        this.name = playerName;
        this.team = team;
        this.position = position;
        this.expectedPoints = expectedPoints;
        this.price = price;
        /*price_x_10 = (int) (price * 10);*/
    }

    public long getSystemTournamentId() {
        return systemTournamentId;
    }

    public void setSystemTournamentId(long systemTournamentId) {
        this.systemTournamentId = systemTournamentId;
    }

    public long getFanteamTournamentId() {
        return fanteamTournamentId;
    }

    public void setFanteamTournamentId(long fanteamTournamentId) {
        this.fanteamTournamentId = fanteamTournamentId;
    }

    public long getFanteamPlayerId() {
        return fanteamPlayerId;
    }

    public void setFanteamPlayerId(long fanteamPlayerId) {
        this.fanteamPlayerId = fanteamPlayerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String playerName) {
        this.name = playerName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getExpectedPoints() {
        return expectedPoints;
    }

    public void setExpectedPoints(double expectedPoints) {
        this.expectedPoints = expectedPoints;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPrice_x_10() {
        return (int) (price * 10);
    }

    @Override
    public String toString() {
        return name + " " + team + " " + price;
    }
}
