package com.hike.digitalgymnastic.tools;

/**
 * Created by liqing on 2016/4/6.
 */
public class EventNotifacation {
    public static   int  Event_clock_set_ok = 1;

    private int mEventType ;

    public  EventNotifacation(int pEentType){
        mEventType = pEentType;
    }

    public int getmEventType() {
        return mEventType;
    }

    public void setmEventType(int mEventType) {
        this.mEventType = mEventType;
    }
}
