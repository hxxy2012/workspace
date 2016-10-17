package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

/**
 * Created by computer on 2016/3/3.
 */
public class Sport implements Serializable {
    private static final long serialVersionUID = 1L;
    private String startTime;
    private String endTime;
    private String steps;
    private String run;
    private String calories;
    private String distance;

    public Sport() {
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSteps() {
        return this.steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getRun() {
        return this.run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getCalories() {
        return this.calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String toString() {
        return "sport startTime[" + this.startTime + "];endTime[" + this.endTime + "];steps[" + this.steps + "];run[" + this.run + "];calories[" + this.calories + "];distance[" + this.distance + "]";
    }
}

