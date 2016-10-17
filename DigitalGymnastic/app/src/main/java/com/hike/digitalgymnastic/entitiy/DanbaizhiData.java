package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//DanbaizhiData(蛋白质比重数据):
public class DanbaizhiData implements Serializable{
	
	DanbaizhiStandard standard;//蛋白质标准	
	private short grade;//蛋白质评级(-1:偏低;0:标准;1:偏高)	
	private double value;//蛋白质值(%)
	public DanbaizhiStandard getStandard() {
		return standard;
	}
	public void setStandard(DanbaizhiStandard standard) {
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
