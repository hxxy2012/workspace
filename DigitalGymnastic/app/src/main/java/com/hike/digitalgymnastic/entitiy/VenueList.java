package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei on 2016/2/22.
 */
public class VenueList implements Serializable {
    List<CustomerVenue> venueList;//	场馆列表
    public List<CustomerVenue> getVenueList() {
        return venueList;
    }

    public void setVenueList(List<CustomerVenue> venueList) {
        this.venueList = venueList;
    }
}
