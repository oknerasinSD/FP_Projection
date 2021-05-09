package com.example.fp_predictor.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String league;
    @Column(name = "startdate")
    private Date startDate;
    @Column(name = "starttime")
    private Time startTime;
    @Column(name = "enddate")
    private Date endDate;
    @Column(name = "endtime")
    private Time endTime;

    public Tournament() {
    }

    public Tournament(String title, String league, Date startDate, Time startTime, Date endDate, Time endTime) {
        this.title = title;
        this.league = league;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public Tournament(String title, String league) {
        this.title = title;
        this.league = league;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
