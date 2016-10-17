package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by computer on 2016/3/3.
 */
public class SportSleepData extends BaseData implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Sport> sportList = new ArrayList();

    public SportSleepData() {
    }

    public void addSport(Sport sport) {
        this.sportList.add(sport);
    }

    public ArrayList<Sport> getSportList() {
        return this.sportList;
    }

    public void setSportList(ArrayList<Sport> sportList) {
        this.sportList = sportList;
    }

    public String toString() {
        return super.toString();
    }
}
