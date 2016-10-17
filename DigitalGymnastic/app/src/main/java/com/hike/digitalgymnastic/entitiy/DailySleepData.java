package com.hike.digitalgymnastic.entitiy;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;
@Table(name="DailySleepData")
public class DailySleepData {
	@Id(column="id")
	@NotNull
	@Unique
	int id;
	@Column(column="statDate")
	private String statDate;//统计日期(yyyy-MM-dd)
	@Column(column="beginTime")
	private String beginTime;//开始时间(HH:mm)
	@Column(column="endTime")
	private String endTime;//结束时间(HH:mm)
	@Column(column="lightTime")
	private long lightTime;//浅层睡眠时间(秒)
	@Column(column="deepTime")
	private long deepTime;//深层睡眠时间(秒)
	@Column(column="totalTime")
	private long totalTime;//总睡眠时间(秒)
	
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
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
	public long getLightTime() {
		return lightTime;
	}
	public void setLightTime(long lightTime) {
		this.lightTime = lightTime;
	}
	public long getDeepTime() {
		return deepTime;
	}
	public void setDeepTime(long deepTime) {
		this.deepTime = deepTime;
	}
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	
}
