package com.hike.digitalgymnastic.entitiy;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

import java.io.Serializable;

@Table(name="OnceSportData")
public class OnceSportData implements Serializable{
	@Id(column="id")
	@NotNull
	@Unique
	int id;
	@Column(column="statDate")
	private String statDate;
	
	@Column(column="onceId")
	private String onceId;
	
	public String getOnceId() {
		return onceId;
	}
	public void setOnceId(String onceId) {
		this.onceId = onceId;
	}
	@Column(column="sportType")
	int sportType;//Integer	
	@Column(column="sportName")
	String sportName;//String	
	@Column(column="beginTime")
	String beginTime;//String	
	@Column(column="endTime")
	String endTime;//String	
	@Column(column="burnCalories")
	double burnCalories;//Double	
	
//	@Foreign(column = "statDate", foreign = "statDate")//前面id为存在本表里面外键的字段名(company_id)，后面id为对应父表主键字段名  
//	DailyTotalSportData dailyTotalSportData;
	
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
//	public DailyTotalSportData getDailyTotalSportData() {
//		return dailyTotalSportData;
//	}
//	public void setDailyTotalSportData(DailyTotalSportData dailyTotalSportData) {
//		this.dailyTotalSportData = dailyTotalSportData;
//	}
	public int getSportType() {
		return sportType;
	}
	public void setSportType(int sportType) {
		this.sportType = sportType;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public double getBurnCalories() {
		return burnCalories;
	}
	public void setBurnCalories(double burnCalories) {
		this.burnCalories = burnCalories;
	}
	
}
