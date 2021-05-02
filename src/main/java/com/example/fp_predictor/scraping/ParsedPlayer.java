package com.example.fp_predictor.scraping;

public class ParsedPlayer {

    private String name;
    private String team;
    private int matchesPlayed;
    private int matchesStarted;
    private int minutesPlayed;
    private int yellowCards;
    private int redCards;
    private double totalXg;
    private double totalXa;
    private double xg90;
    private double xa90;

    public ParsedPlayer(String name, String team, int matchesPlayed, int matchesStarted, int minutesPlayed,
                        int yellowCards, int redCards, double totalXg, double totalXa, double xg90, double xa90)
    {
        this.name = name;
        this.team = team;
        this.matchesPlayed = matchesPlayed;
        this.matchesStarted = matchesStarted;
        this.minutesPlayed = minutesPlayed;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.totalXg = totalXg;
        this.totalXa = totalXa;
        this.xg90 = xg90;
        this.xa90 = xa90;
    }

    @Override
    public String toString() {
        return "ParsedPlayer{" +
                "name='" + name + '\'' +
                ", matchesPlayed=" + matchesPlayed +
                ", matchesStarted=" + matchesStarted +
                ", minutesPlayed=" + minutesPlayed +
                ", yellowCards=" + yellowCards +
                ", redCards=" + redCards +
                ", totalXg=" + totalXg +
                ", totalXa=" + totalXa +
                ", xg90=" + xg90 +
                ", xa90=" + xa90 +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getMatchesStarted() {
        return matchesStarted;
    }

    public void setMatchesStarted(int matchesStarted) {
        this.matchesStarted = matchesStarted;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public double getTotalXg() {
        return totalXg;
    }

    public void setTotalXg(double totalXg) {
        this.totalXg = totalXg;
    }

    public double getTotalXa() {
        return totalXa;
    }

    public void setTotalXa(double totalXa) {
        this.totalXa = totalXa;
    }

    public double getXg90() {
        return xg90;
    }

    public void setXg90(double xg90) {
        this.xg90 = xg90;
    }

    public double getXa90() {
        return xa90;
    }

    public void setXa90(double xa90) {
        this.xa90 = xa90;
    }
}
