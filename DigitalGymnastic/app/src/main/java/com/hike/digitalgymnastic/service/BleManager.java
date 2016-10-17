package com.hike.digitalgymnastic.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.hike.digitalgymnastic.entitiy.MyClock;

import java.util.ArrayList;

/**
 * Created by hiko on 2016/1/19.
 */
public class BleManager {
    BleBaseControler controler;
    private static BleManager mBleManager;
    private  Context context;
    private  Handler mHandler;
    private BleManager(){

    }
    private BleManager(Context context,Handler mHandler){
        this.context=context;
        this.mHandler=mHandler;
    }

    public  void init(int type) {
        switch (type){
            case 1:
                controler=BleBaseControlerLOVEFIT.getInstance(context,mHandler);
                break;
        }
    }

    public synchronized  static BleManager getInstance(Context context,Handler mHandler){
        if(mBleManager==null){
            mBleManager=new BleManager(context,mHandler);
        }
        return mBleManager;
    }
    public void stopScan(){
        if (controler!=null){
            controler.stopScan();
        }
    }

    public void shutdown(){
        if(controler!=null){
            controler.shutdown();
            controler=null;
            mBleManager=null;
        }
    }

    public boolean isShutdown(){
        if(controler!=null){
            return controler.isShutdown();
        }
        return true;

    }

    public void scanDevice(int pType) {
        if(controler!=null){
            controler.scanDevice(pType);
        }
    }

    public boolean connect(String mac) {
        if(controler!=null){
           return  controler.connect(mac);
        }
        return false;
    }

    public void disconnect() {
        if(controler!=null){
            controler.disconnect();
        }
    }

    public void bindUser(String user) {
        if(controler!=null){
            controler.bindUser(user);
        }
    }

    public void unBinderUer(String user) {

        if(controler!=null){
            controler.unBinderUer(user);
        }
    }

    public void setDeviceName(String name) {
        if(controler!=null){
            controler.setDeviceName(name);
        }
    }

    public void getlaseFillPowerTime() {
        if(controler!=null){
            controler.getlaseFillPowerTime();
        }
    }

    public void getBallary() {
        if(controler!=null){
            controler.getBallary();
        }
    }

    public void adjustTime() {
        if(controler!=null){
            controler.adjustTime();
        }
    }

    public void getRecordCount() {
        if(controler!=null){
            controler.getRecordCount();
        }
    }

    public void syncWatchData(int tag) {
        if(controler!=null){
            controler.syncWatchData(tag);
        }
    }

    public void deviceUpdate() {
        if(controler!=null){
            controler.deviceUpdate();
        }
    }

    public void syncWatchSleepData() {
        if(controler!=null){
            controler.syncWatchSleepData();
        }
    }

    public void setAlarm(ArrayList<MyClock> pClock) {
        if (controler!=null){
            controler.setAlarm(pClock);
        }
    }

    public void getConfig() {
        if (controler!=null){
            controler.getConfig();
        }
    }
}
