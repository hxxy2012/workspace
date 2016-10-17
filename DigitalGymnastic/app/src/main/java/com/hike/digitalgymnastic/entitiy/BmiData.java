package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//BmiData(体质指数数据):
public class BmiData implements Serializable{
	private BmiStandard standard;//体质指数标准
	private short grade;//体质指数评级(-1:较轻;0:正常;1:适中;2:过重;3:肥胖;4:非常胖)
	private double value;//体质指数值
	public BmiStandard getStandard() {
		return standard;
	}
	public void setStandard(BmiStandard standard) {
		this.standard = standard;
	}
	public short getGrade() {
		return grade;
	}
	public void setGrade(short grade) {
		this.grade = grade;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
