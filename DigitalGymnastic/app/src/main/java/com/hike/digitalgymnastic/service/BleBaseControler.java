package com.hike.digitalgymnastic.service;

import com.hike.digitalgymnastic.entitiy.MyClock;

import java.util.ArrayList;

/**
 * Created by hiko on 2016/1/19.
 */
public interface BleBaseControler {
    //扫描设备
    public  void  scanDevice(int pType);
    // 连接设备
    public  boolean  connect(String mac);
    // 断开
    public  void  disconnect();
    // 绑定用户
    public  void  bindUser(String user);
    // 解绑用户
    public  void  unBinderUer(String user);
    // 设置昵称
    public  void  setDeviceName(String name);
    // 获取上次充电时间
    public void getlaseFillPowerTime();
    // 获取电量
    public void getBallary();
    //设置时间
    public void adjustTime();
    //获取数据统计
    public void getRecordCount();
    //同步数据
    public void syncWatchData(int tag);
    //设备升级
    public void deviceUpdate();

    public void shutdown();
    public boolean   isShutdown();

    public void syncWatchSleepData();

    public void stopScan();

    public void setAlarm(ArrayList<MyClock> pClock);

    void getConfig();
}