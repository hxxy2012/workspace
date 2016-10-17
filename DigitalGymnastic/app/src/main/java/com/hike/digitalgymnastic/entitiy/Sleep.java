package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

/**
 * Created by computer on 2016/3/3.
 */
public class Sleep implements Serializable {
    private static final long serialVersionUID = 1L;
    private String time;
    private int type;
    private int turnOver;

    public Sleep() {
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTurnOver() {
        return this.turnOver;
    }

    public void setTurnOver(int turnOver) {
        this.turnOver = turnOver;
    }

    public String toString() {
        return "sleep time[" + this.time + "];type[" + this.type + "];turn[" + this.turnOver + "]";
    }
}

