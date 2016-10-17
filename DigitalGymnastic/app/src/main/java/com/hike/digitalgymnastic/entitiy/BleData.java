package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

/**
 * Created by computer on 2016/3/3.
 */
public class BleData<T> extends BaseData implements Serializable {
    private static final long serialVersionUID = 1L;
    private volatile T mData;

    public BleData() {
    }

    public synchronized boolean isDataLegal() {
        return this.mData != null;
    }

    public synchronized void setData(T data) {
        this.mData = data;
    }

    public synchronized T getData() {
        return this.mData;
    }

    public synchronized String toString() {
        return "isTimeOut[" + this.mIsTimeOut + "];desc[" + this.mDesc + "];data[" + this.mData + "]";
    }
}
