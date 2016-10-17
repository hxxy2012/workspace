package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//ZhifanglvData(身体脂肪率数据):
public class NeizangzhifangData implements Serializable{
	private NeizangzhifangStandard standard;//内脏脂肪指数标准
	private short grade;//内脏脂肪指数评级(-1:皮下型;0:均衡型;1:警戒;2:肥胖;3:非常肥胖)	
	private int value;//内脏脂肪指数值
	public NeizangzhifangStandard getStandard() {
		return standard;
	}
	public void setStandard(NeizangzhifangStandard standard) {
		this.standard = standard;
	}
	public short getGrade() {
		return grade;
	}
	public void setGrade(short grade) {
		this.grade = grade;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
