package com.hike.digitalgymnastic.entitiy;

import java.util.List;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;
@Table(name="DailyTotalSportData")
public class DailyTotalSportData {
	@Id(column="id")
	@NotNull
	@Unique
	int id;
	@Column(column="statDate")
	private String statDate;
	@Column(column="totalTime")
	private long totalTime;
	@Column(column="totalCalories")
	private double totalCalories;
	
	private List<OnceSportData> onceList;
	
	
	
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public double getTotalCalories() {
		return totalCalories;
	}
	public void setTotalCalories(double totalCalories) {
		this.totalCalories = totalCalories;
	}
	public List<OnceSportData> getOnceList() {
		return onceList;
	}
	public void setOnceList(List<OnceSportData> onceList) {
		this.onceList = onceList;
	}
	
}
