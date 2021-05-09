package com.example.fp_predictor.domain;

import com.example.fp_predictor.converter.TimeConverter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    private long fanteam_id;
    private String title;
    private String league;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private String filename = "";

    public Tournament() {
    }

    public Tournament(
            String title,
            String league,
            Date startDate,
            Time startTime,
            Date endDate,
            Time endTime
    ) {
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

    public String getStringStartTime() {
        TimeConverter converter = new TimeConverter();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
        String result = converter.fromDatabase(startTime).format(formatter);
        if ("24:00".equals(result)) {
            result = "00:00";
        }
        return result;
    }

    public String getStringEndTime() {
        TimeConverter converter = new TimeConverter();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
        String result = converter.fromDatabase(endTime).format(formatter);
        if ("24:00".equals(result)) {
            result = "00:00";
        }
        return result;
    }

    public String convertId() {
        return String.valueOf(id);
    }

    public long getFanteam_id() {
        return fanteam_id;
    }

    public void setFanteam_id(long fanteam_id) {
        this.fanteam_id = fanteam_id;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
