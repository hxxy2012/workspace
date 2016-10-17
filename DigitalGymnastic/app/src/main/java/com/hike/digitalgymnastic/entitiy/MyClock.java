package com.hike.digitalgymnastic.entitiy;

import com.hike.digitalgymnastic.utils.Utils;

import java.util.List;

public class MyClock {
	private int group_id;
	private List<Boolean> idList;
	private String workTime;
	private int hour;
	private int min;
	private boolean openorclose;//开启or关闭
	private boolean isExpanded;//是否展开

	public boolean isExpanded() {
		return isExpanded;
	}


	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public MyClock(){
		
	}

	public int getGroup_id() {
		return group_id;
	}


	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}


	public List<Boolean> getIdList() {
		return idList;
	}


	public void setIdList(List<Boolean> idList) {
		this.idList = idList;
	}


	public int getHour() {
		return hour;
	}


	public void setHour(int hour) {
		this.hour = hour;
	}


	public int getMin() {
		return min;
	}


	public void setMin(int min) {
		this.min = min;
	}


	public boolean isOpenorclose() {
		return openorclose;
	}

	public void setOpenorclose(boolean openorclose) {
		this.openorclose = openorclose;
	}

	public String getWorkTime() {
		return Utils.getmWeekListString(this.idList);
	}

	public void setWorkTime(String workTime) {
		this.setIdList(Utils.getmWeekList(workTime));
		this.workTime = workTime;
	}

}
