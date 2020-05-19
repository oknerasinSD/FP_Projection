package com.example.fp_predictor.domain;

import javax.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double expectedPoints;

    public Player() {
    }

    public Player(String playerName, double expectedPoints) {
        this.id = id;
        this.name = playerName;
        this.expectedPoints = expectedPoints;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExpectedPoints() {
        return expectedPoints;
    }

    public void setExpectedPoints(double expectedPoints) {
        this.expectedPoints = expectedPoints;
    }
}
