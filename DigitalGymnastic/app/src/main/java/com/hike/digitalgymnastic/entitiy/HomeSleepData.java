package com.hike.digitalgymnastic.entitiy;

import java.util.List;

public class HomeSleepData {
	private String beginTime;//开始时间(HH:mm)
	private String endTime;//结束时间(HH:mm)
	private long lightTime;//浅层睡眠时间(秒)
	private long deepTime;//深层睡眠时间(秒)
	private long totalTime;//总睡眠时间(秒)
	private long sdeepTime;//标准深层睡眠时间(秒)
	private long stotalTime;//标准总睡眠时间(秒)
	private short sleepQuality;//睡眠质量(1:优质;2:良好;3:一般;4:较差)
	private String adviceTitle;//建议标题
	private String adviceContent;//建议内容
	private List<SectionSleepData> dataList;
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
	public long getSdeepTime() {
		return sdeepTime;
	}
	public void setSdeepTime(long sdeepTime) {
		this.sdeepTime = sdeepTime;
	}
	public long getStotalTime() {
		return stotalTime;
	}
	public void setStotalTime(long stotalTime) {
		this.stotalTime = stotalTime;
	}
	public short getSleepQuality() {
		return sleepQuality;
	}
	public void setSleepQuality(short sleepQuality) {
		this.sleepQuality = sleepQuality;
	}
	public String getAdviceTitle() {
		return adviceTitle;
	}
	public void setAdviceTitle(String adviceTitle) {
		this.adviceTitle = adviceTitle;
	}
	public String getAdviceContent() {
		return adviceContent;
	}
	public void setAdviceContent(String adviceContent) {
		this.adviceContent = adviceContent;
	}
	public List<SectionSleepData> getDataList() {
		return dataList;
	}
	public void setDataList(List<SectionSleepData> dataList) {
		this.dataList = dataList;
	}

	
}
