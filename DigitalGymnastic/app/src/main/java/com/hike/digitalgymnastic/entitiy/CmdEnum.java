package com.hike.digitalgymnastic.entitiy;

/**
 * Created by computer on 2016/3/3.
 */
public enum CmdEnum {
    Bind(100),
    RecordCount(110),
    Battery(120),
    Version(130),
    GetAlarmClock(140),
    SetTime(150),
    SetName(160),
    SportData(170),
    SetAlarmClock(180),
    DeleteSportData(190),
    UnreadCount(200),
    ReadName(210);

    private int code;

    private CmdEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public int value() {
        return this.code;
    }
}

